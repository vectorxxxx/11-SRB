package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.srb.core.enums.BorrowerStatusEnum;
import xyz.funnyboy.srb.core.mapper.BorrowerMapper;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.entity.BorrowerAttach;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerVO;
import xyz.funnyboy.srb.core.service.BorrowerAttachService;
import xyz.funnyboy.srb.core.service.BorrowerService;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService
{

    @Resource
    private BorrowerAttachService borrowerAttachService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 保存借款人
     *
     * @param borrowerVO 借款人VO
     * @param userId     用户 ID
     */
    @Override
    public void saveBorrower(BorrowerVO borrowerVO, Long userId) {
        final UserInfo userInfo = userInfoService.getById(userId);

        // 保存借款人信息
        final Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setMobile(userInfo.getMobile());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);

        // 保存附件
        final List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(attach -> attach.setBorrowerId(borrower.getId()));
        borrowerAttachService.saveBatch(borrowerAttachList);

        // 更新会员状态为认证中
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoService.updateById(userInfo);
    }

    /**
     * 获取借款人认证状态
     *
     * @param userId 用户 ID
     * @return {@link Integer}
     */
    @Override
    public Integer getBorrowerStatus(Long userId) {
        // 查询认证状态
        final List<Object> statusList = baseMapper.selectObjs(new LambdaQueryWrapper<Borrower>()
                .select(Borrower::getStatus)
                .eq(Borrower::getUserId, userId));

        // 如果为空，则未认证
        if (CollectionUtils.isEmpty(statusList)) {
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }

        // 返回认证状态
        return (Integer) statusList.get(0);
    }
}
