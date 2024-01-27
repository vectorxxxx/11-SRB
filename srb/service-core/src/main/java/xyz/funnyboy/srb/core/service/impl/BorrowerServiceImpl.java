package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.srb.core.enums.BorrowerStatusEnum;
import xyz.funnyboy.srb.core.enums.IntegralEnum;
import xyz.funnyboy.srb.core.mapper.BorrowerMapper;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.entity.BorrowerAttach;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.entity.UserIntegral;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerApprovalVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerDetailVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerVO;
import xyz.funnyboy.srb.core.service.*;

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

    @Resource
    private DictService dictService;

    @Resource
    private UserIntegralService userIntegralService;

    /**
     * 保存借款人
     *
     * @param borrowerVO 借款人VO
     * @param userId     用户 ID
     */
    @Transactional(rollbackFor = Exception.class)
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

    /**
     * 获取借款人分页列表
     *
     * @param pageParam 页面参数
     * @param keyword   关键词
     * @return {@link Page}<{@link Borrower}>
     */
    @Override
    public Page<Borrower> listPage(Page<Borrower> pageParam, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return baseMapper.selectPage(pageParam, null);
        }

        // 查询姓名、身份证号、手机号
        final LambdaQueryWrapper<Borrower> queryWrapper = new LambdaQueryWrapper<Borrower>()
                .like(Borrower::getName, keyword)
                .or()
                .like(Borrower::getIdCard, keyword)
                .or()
                .like(Borrower::getMobile, keyword)
                .orderByDesc(Borrower::getId);
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 获取借款人信息
     *
     * @param id 编号
     * @return {@link BorrowerDetailVO}
     */
    @Override
    public BorrowerDetailVO getBorrowerDetailVOById(Long id) {
        // 查询借款人信息
        final Borrower borrower = baseMapper.selectById(id);

        // 填充借款人信息
        BorrowerDetailVO borrowerDetailVO = new BorrowerDetailVO();
        BeanUtils.copyProperties(borrower, borrowerDetailVO);

        // 婚否
        borrowerDetailVO.setMarry(borrower.getMarry() ?
                                  "是" :
                                  "否");
        // 性别
        borrowerDetailVO.setSex(borrower.getSex() == 1 ?
                                "男" :
                                "女");

        // 计算下拉列表选中内容
        borrowerDetailVO.setEducation(dictService.getNameByParentDictCodeAndValue("education", borrower.getEducation()));
        borrowerDetailVO.setIndustry(dictService.getNameByParentDictCodeAndValue("industry", borrower.getIndustry()));
        borrowerDetailVO.setIncome(dictService.getNameByParentDictCodeAndValue("income", borrower.getIncome()));
        borrowerDetailVO.setReturnSource(dictService.getNameByParentDictCodeAndValue("returnSource", borrower.getReturnSource()));
        borrowerDetailVO.setContactsRelation(dictService.getNameByParentDictCodeAndValue("relation", borrower.getContactsRelation()));

        // 审批状态
        borrowerDetailVO.setStatus(BorrowerStatusEnum.getMsgByStatus(borrower.getStatus()));

        // 获取附件VO列表
        borrowerDetailVO.setBorrowerAttachVOList(borrowerAttachService.selectBorrowerAttachVOList(id));

        return borrowerDetailVO;
    }

    /**
     * 借款额度审批
     *
     * @param borrowerApprovalVO 借款人批准VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(BorrowerApprovalVO borrowerApprovalVO) {
        // 借款人认证状态
        final Borrower borrower = baseMapper.selectById(borrowerApprovalVO.getBorrowerId());
        borrower.setStatus(borrowerApprovalVO.getStatus());
        baseMapper.updateById(borrower);

        // 添加积分
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(borrower.getUserId());
        userIntegral.setIntegral(borrowerApprovalVO.getInfoIntegral());
        userIntegral.setContent("借款人基本信息");
        userIntegralService.save(userIntegral);

        final UserInfo userInfo = userInfoService.getById(borrower.getUserId());
        int curIntegral = userInfo.getIntegral() + borrowerApprovalVO.getInfoIntegral();
        if (borrowerApprovalVO.getIsIdCardOk()) {
            curIntegral += IntegralEnum.BORROWER_IDCARD.getIntegral();
            userIntegral = new UserIntegral();
            userIntegral.setUserId(borrower.getUserId());
            userIntegral.setIntegral(IntegralEnum.BORROWER_IDCARD.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_IDCARD.getMsg());
            userIntegralService.save(userIntegral);
        }
        if (borrowerApprovalVO.getIsCarOk()) {
            curIntegral += IntegralEnum.BORROWER_CAR.getIntegral();
            userIntegral = new UserIntegral();
            userIntegral.setUserId(borrower.getUserId());
            userIntegral.setIntegral(IntegralEnum.BORROWER_CAR.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_CAR.getMsg());
            userIntegralService.save(userIntegral);
        }
        if (borrowerApprovalVO.getIsHouseOk()) {
            curIntegral += IntegralEnum.BORROWER_HOUSE.getIntegral();
            userIntegral = new UserIntegral();
            userIntegral.setUserId(borrower.getUserId());
            userIntegral.setIntegral(IntegralEnum.BORROWER_HOUSE.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_HOUSE.getMsg());
            userIntegralService.save(userIntegral);
        }

        // 更新用户积分
        userInfo.setIntegral(curIntegral);
        userInfo.setBorrowAuthStatus(borrowerApprovalVO.getStatus());
        userInfoService.updateById(userInfo);
    }
}
