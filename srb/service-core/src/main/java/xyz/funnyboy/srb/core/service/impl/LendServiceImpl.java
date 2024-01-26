package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.enums.LendStatusEnum;
import xyz.funnyboy.srb.core.mapper.LendMapper;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.pojo.vo.BorrowInfoApprovalVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerDetailVO;
import xyz.funnyboy.srb.core.service.BorrowerService;
import xyz.funnyboy.srb.core.service.DictService;
import xyz.funnyboy.srb.core.service.LendService;
import xyz.funnyboy.srb.core.utils.LendNoUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService
{
    @Resource
    private DictService dictService;

    @Resource
    private BorrowerService borrowerService;

    /**
     * 创建标的
     *
     * @param borrowInfoApprovalVO 借款信息审批VO
     * @param borrowInfo           借款信息
     */
    @Override
    public void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo) {
        final Lend lend = new Lend();
        // 借款用户id
        lend.setUserId(borrowInfo.getUserId());
        // 借款信息id
        lend.setBorrowInfoId(borrowInfo.getId());
        // 标的编号
        lend.setLendNo(LendNoUtils.getLendNo());
        // 标题
        lend.setTitle(borrowInfoApprovalVO.getTitle());
        // 标的金额
        lend.setAmount(borrowInfo.getAmount());
        // 投资期数
        lend.setPeriod(borrowInfo.getPeriod());

        // 年化利率
        lend.setLendYearRate(borrowInfoApprovalVO
                .getLendYearRate()
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        // 平台服务费率
        lend.setServiceRate(borrowInfoApprovalVO
                .getServiceRate()
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));

        // 还款方式
        lend.setReturnMethod(borrowInfo.getReturnMethod());
        // 最低投资金额
        lend.setLowestAmount(new BigDecimal(100));
        // 已投金额
        lend.setInvestAmount(new BigDecimal(0));
        // 投资人数
        lend.setInvestNum(0);
        // 发布日期
        lend.setPublishDate(LocalDateTime.now());

        // 起息日期
        final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate lendStartDate = LocalDate.parse(borrowInfoApprovalVO.getLendStartDate(), pattern);
        lend.setLendStartDate(lendStartDate);
        // 结束日期
        final LocalDate lendEndDate = lendStartDate.plusMonths(borrowInfo.getPeriod());
        lend.setLendEndDate(lendEndDate);

        // 说明
        lend.setLendInfo(borrowInfoApprovalVO.getLendInfo());

        //平台预期收益
        //        月年化 = 年化 / 12
        BigDecimal monthRate = lend
                .getServiceRate()
                .divide(new BigDecimal(12), 8, BigDecimal.ROUND_DOWN);
        //        平台收益 = 标的金额 * 月年化 * 期数
        BigDecimal expectAmount = lend
                .getAmount()
                .multiply(monthRate)
                .multiply(new BigDecimal(borrowInfo.getPeriod()));
        lend.setExpectAmount(expectAmount);

        // 实际收益
        lend.setRealAmount(new BigDecimal("0"));
        // 状态
        lend.setStatus(LendStatusEnum.INVEST_RUN.getStatus());
        // 审核时间
        lend.setCheckTime(LocalDateTime.now());
        // 审核用户id
        lend.setCheckAdminId(1L);

        baseMapper.insert(lend);
    }

    /**
     * 查询标的列表
     *
     * @return {@link List}<{@link Lend}>
     */
    @Override
    public List<Lend> selectList() {
        final List<Lend> lendList = baseMapper.selectList(null);
        lendList.forEach(lend -> {
            lend
                    .getParam()
                    .put("returnMethod", dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod()));
            lend
                    .getParam()
                    .put("status", LendStatusEnum.getMsgByStatus(lend.getStatus()));
        });
        return lendList;
    }

    /**
     * 获取标的详情
     *
     * @param id 编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getLendDetail(Long id) {
        // 获取标的详情
        final Lend lend = baseMapper.selectById(id);
        lend
                .getParam()
                .put("returnMethod", dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod()));
        lend
                .getParam()
                .put("status", LendStatusEnum.getMsgByStatus(lend.getStatus()));

        // 获取借款人信息
        final Borrower borrower = borrowerService.getOne(new LambdaQueryWrapper<Borrower>().eq(Borrower::getUserId, lend.getUserId()));
        final BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(borrower.getId());

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("lend", lend);
        result.put("borrower", borrowerDetailVO);
        return result;
    }
}
