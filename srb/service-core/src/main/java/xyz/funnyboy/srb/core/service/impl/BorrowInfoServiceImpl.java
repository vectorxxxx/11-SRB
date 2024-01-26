package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.enums.BorrowInfoStatusEnum;
import xyz.funnyboy.srb.core.enums.BorrowerStatusEnum;
import xyz.funnyboy.srb.core.enums.UserBindEnum;
import xyz.funnyboy.srb.core.mapper.BorrowInfoMapper;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.pojo.entity.IntegralGrade;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.service.BorrowInfoService;
import xyz.funnyboy.srb.core.service.IntegralGradeService;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService
{
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private IntegralGradeService integralGradeService;

    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        // 获取用户积分
        final UserInfo userInfo = userInfoService.getById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        final Integer integral = userInfo.getIntegral();

        // 根据积分查询借款额度
        final IntegralGrade integralGrade = integralGradeService.getOne(new LambdaQueryWrapper<IntegralGrade>()
                .le(IntegralGrade::getIntegralStart, integral)
                .ge(IntegralGrade::getIntegralEnd, integral));
        if (integralGrade == null) {
            return BigDecimal.ZERO;
        }
        return integralGrade.getBorrowAmount();
    }

    /**
     * 提交借款申请
     *
     * @param borrowInfo 借款信息
     * @param userId     用户 ID
     */
    @Override
    public void saveBorrowInfo(BorrowInfo borrowInfo, Long userId) {
        // 获取用户信息
        final UserInfo userInfo = userInfoService.getById(userId);

        // 用户绑定状态
        Assert.isTrue(userInfo
                .getBindStatus()
                .intValue() == UserBindEnum.BIND_OK
                .getStatus()
                .intValue(), ResponseEnum.USER_NO_BIND_ERROR);

        // 用户审批状态
        Assert.isTrue(userInfo
                .getBorrowAuthStatus()
                .intValue() == BorrowerStatusEnum.AUTH_OK
                .getStatus()
                .intValue(), ResponseEnum.USER_NO_AUTH_ERROR);

        // 借款额度是否足够
        final BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.isTrue(borrowInfo
                .getAmount()
                .intValue() <= borrowAmount.intValue(), ResponseEnum.USER_AMOUNT_LESS_ERROR);

        borrowInfo.setUserId(userId);
        // 年化利率
        borrowInfo.setBorrowYearRate(borrowInfo
                .getBorrowYearRate()
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        // 审核状态
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getStatus());
        baseMapper.insert(borrowInfo);
    }

    /**
     * 获取借款申请审批状态
     *
     * @param userId 用户 ID
     * @return {@link Integer}
     */
    @Override
    public Integer getBorrowInfoStatus(Long userId) {
        final List<Object> statusList = baseMapper.selectObjs(new LambdaQueryWrapper<BorrowInfo>()
                .select(BorrowInfo::getStatus)
                .eq(BorrowInfo::getUserId, userId));
        if (CollectionUtils.isEmpty(statusList)) {
            return BorrowInfoStatusEnum.NO_AUTH.getStatus();
        }
        return (Integer) statusList.get(0);
    }
}
