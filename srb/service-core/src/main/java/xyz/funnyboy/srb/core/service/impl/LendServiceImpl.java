package xyz.funnyboy.srb.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.funnyboy.common.exception.BusinessException;
import xyz.funnyboy.srb.core.enums.LendStatusEnum;
import xyz.funnyboy.srb.core.enums.ReturnMethodEnum;
import xyz.funnyboy.srb.core.enums.TransTypeEnum;
import xyz.funnyboy.srb.core.hfb.HfbConst;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.mapper.LendMapper;
import xyz.funnyboy.srb.core.mapper.UserAccountMapper;
import xyz.funnyboy.srb.core.mapper.UserInfoMapper;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.*;
import xyz.funnyboy.srb.core.pojo.vo.BorrowInfoApprovalVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerDetailVO;
import xyz.funnyboy.srb.core.service.*;
import xyz.funnyboy.srb.core.utils.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
@Slf4j
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService
{
    @Resource
    private DictService dictService;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private LendItemService lendItemService;

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private LendReturnService lendReturnService;

    @Resource
    private LendItemReturnService lendItemReturnService;

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
        lend.setRealAmount(BigDecimal.ZERO);
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

    /**
     * 计算投资收益
     *
     * @param invest       投资
     * @param yearRate     年费率
     * @param totalmonth   期数
     * @param returnMethod 还款方式
     * @return {@link BigDecimal}
     */
    @Override
    public BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod) {
        BigDecimal interestCount = BigDecimal.ZERO;
        if (returnMethod.intValue() == ReturnMethodEnum.ONE
                .getMethod()
                .intValue()) {
            log.info("等额本息计算投资收益");
            interestCount = Amount1Helper.getInterestCount(invest, yearRate, totalmonth);
        }
        else if (returnMethod.intValue() == ReturnMethodEnum.TWO
                .getMethod()
                .intValue()) {
            log.info("等额本金计算投资收益");
            interestCount = Amount2Helper.getInterestCount(invest, yearRate, totalmonth);
        }
        else if (returnMethod.intValue() == ReturnMethodEnum.THREE
                .getMethod()
                .intValue()) {
            log.info("每月还息一次还本计算投资收益");
            interestCount = Amount3Helper.getInterestCount(invest, yearRate, totalmonth);
        }
        else if (returnMethod.intValue() == ReturnMethodEnum.FOUR
                .getMethod()
                .intValue()) {
            log.info("一次还本还息计算投资收益");
            interestCount = Amount4Helper.getInterestCount(invest, yearRate, totalmonth);
        }
        return interestCount;
    }

    /**
     * 满标放款
     *
     * @param lendId 编号
     */
    @Override
    public void makeLoan(Long lendId) {
        // 标的信息
        final Lend lend = baseMapper.selectById(lendId);

        // 平台收益，放款扣除，借款人借款实际金额=借款金额-平台收益
        // 月年化
        final BigDecimal monthRate = lend
                .getServiceRate()
                .divide(BigDecimal.valueOf(12), 8, BigDecimal.ROUND_HALF_DOWN);
        // 平台实际收益 = 已投金额 * 月年化 * 标的期数
        final BigDecimal realAmount = lend
                .getInvestAmount()
                .multiply(monthRate)
                .multiply(BigDecimal.valueOf(lend.getPeriod()));

        // ============调用满标放款接口============
        Map<String, Object> paramMap = new HashMap<>();
        // agent_id	int		是	给商户分配的唯一标识
        paramMap.put("agentId", HfbConst.AGENT_ID);
        // agent_project_code	string	50	是	放款项目编号。只能由数字、字母组成。
        //      同一商户，所有订单号（投资单号、放款单号、转账单号）必须唯一。
        paramMap.put("agentProjectCode", lend.getLendNo());
        // agent_bill_no	string	50	是	放款单号只能由数字、字母组成字符。
        //      同一商户，所有订单号（投资单号、放款单号、转账单号）必须唯一。
        final String agentBillNo = LendNoUtils.getLoanNo();
        paramMap.put("agentBillNo", agentBillNo);
        // mch_fee	decimal		是	商户手续费
        paramMap.put("mchFee", realAmount);
        // note	string	255	否	放款备注。
        paramMap.put("note", "发起放款");
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        // sign	string	32	是	验签参数。
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        log.info("发起放款入参：{}", JSON.toJSONString(paramMap));
        final JSONObject result = RequestHelper.sendRequest(paramMap, HfbConst.MAKE_LOAN_URL);
        log.info("发起放款出参：{}", result);

        // result_code	string		是	结果编码：
        //      0000=放款成功
        //      E100=商户授权错误
        //      E101=签名错误
        //      E103=时间戳无效
        //      E104=参数不全或不合法
        //      E105=接收放款失败
        //      U999=未知错误
        // result_msg	string	100	是	接收放款结果描述
        if (!"0000".equals(result.getString("resultCode"))) {
            throw new BusinessException("发起放款失败，接收放款结果描述：" + result.getString("resultMsg"));
        }

        // ============更新标的信息============
        lend.setRealAmount(realAmount);
        lend.setStatus(LendStatusEnum.PAY_RUN.getStatus());
        lend.setPaymentTime(LocalDateTime.now());
        baseMapper.updateById(lend);

        // ============更新借款信息============
        // 获取借款人信息
        final String bindCode = userInfoMapper
                .selectById(lend.getUserId())
                .getBindCode();

        // 给借款账号转入金额
        // vote_amt	decimal		是	放款金额(包含投资奖励金额)
        final BigDecimal total = BigDecimal.valueOf(result.getDouble("voteAmt"));
        userAccountMapper.updateAccount(bindCode, total, BigDecimal.ZERO);

        // 新增借款人交易流水
        // agent_bill_no	string	50	是	放款单号
        final String memo1 = String.format("借款放款到账，编号：%s", lend.getLendNo());
        transFlowService.saveTransFlow(new TransFlowBO(agentBillNo, bindCode, total, TransTypeEnum.BORROW_BACK, memo1));

        // ============更新投资信息============
        lendItemService
                .selectByLendId(lendId, 1)
                .forEach(lendItem -> {
                    // 获取投资人信息
                    final String investBindCode = userInfoMapper
                            .selectById(lendItem.getInvestUserId())
                            .getBindCode();

                    // 投资人账号冻结金额转出
                    final BigDecimal investAmount = lendItem.getInvestAmount();
                    // negate()将值取相反数
                    userAccountMapper.updateAccount(investBindCode, BigDecimal.ZERO, investAmount.negate());

                    // 新增投资人交易流水
                    final String memo2 = String.format("冻结资金转出，出借放款，编号：%s", lend.getLendNo());
                    transFlowService.saveTransFlow(new TransFlowBO(LendNoUtils.getTransNo(), investBindCode, investAmount, TransTypeEnum.INVEST_UNLOCK, memo2));
                });

        // 放款成功生成 [借款人还款计划] 和 [投资人回款计划]
        this.repaymentPlan(lend);
    }

    /**
     * 还款计划
     *
     * @param lend 标的信息
     */
    private void repaymentPlan(Lend lend) {
        //======================================================
        //===============按还款时间生成还款计划=====================
        //======================================================
        final ArrayList<LendReturn> lendReturnList = new ArrayList<>();
        final Integer len = lend.getPeriod();
        for (int i = 1; i <= len; i++) {
            final LendReturn lendReturn = new LendReturn();
            // 标的id
            lendReturn.setLendId(lend.getId());
            // 借款信息id
            lendReturn.setBorrowInfoId(lend.getBorrowInfoId());
            // 还款批次号
            lendReturn.setReturnNo(LendNoUtils.getReturnNo());
            // 借款人用户id
            lendReturn.setUserId(lend.getUserId());
            // 借款金额
            lendReturn.setAmount(lend.getAmount());
            // 计息本金额
            lendReturn.setBaseAmount(lend.getInvestAmount());
            // 当前的期数
            lendReturn.setCurrentPeriod(i);
            // 年化利率
            lendReturn.setLendYearRate(lend.getLendYearRate());
            // 还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本
            lendReturn.setReturnMethod(lend.getReturnMethod());

            // 说明：还款计划中的这三项 = 回款计划中对应的这三项和：因此需要先生成对应的回款计划
            // 本金
            // lendReturn.setPrincipal(BigDecimal.ZERO);
            // 利息
            // lendReturn.setInterest(BigDecimal.ZERO);
            // 本息
            // lendReturn.setTotal(BigDecimal.ZERO);

            // 手续费
            lendReturn.setFee(BigDecimal.ZERO);
            // 还款时指定的还款日期
            lendReturn.setReturnDate(lend.getLendStartDate());
            // 是否逾期
            lendReturn.setOverdue(false);
            // 是否最后一次还款
            lendReturn.setLast(i == len);
            // 状态（0-未归还 1-已归还）
            lendReturn.setStatus(0);

            lendReturnList.add(lendReturn);
        }
        lendReturnService.saveBatch(lendReturnList);

        // 获取lendReturnList中还款期数与还款计划id对应map
        final Map<Integer, Long> lendReturnMap = lendReturnList
                .stream()
                .collect(Collectors.toMap(LendReturn::getCurrentPeriod, LendReturn::getId));

        //======================================================
        //=============获取所有投资者，生成回款计划===================
        //======================================================
        // 回款计划列表
        List<LendItemReturn> lendItemReturnList = lendItemService
                // 获取投资成功的投资记录
                .selectByLendId(lend.getId(), 1)
                .stream()
                .flatMap(lendItem -> this
                        .returnInvest(lendItem.getId(), lendReturnMap, lend)
                        .stream())
                .collect(Collectors.toList());
        final Map<Long, List<LendItemReturn>> lendItemReturnListMap = lendItemReturnList
                .stream()
                .collect(Collectors.groupingBy(LendItemReturn::getLendReturnId));

        // 更新还款计划中的相关金额数据
        lendReturnList.forEach(lendReturn -> {
            final List<LendItemReturn> lendItemReturnByLendIdList = lendItemReturnListMap.getOrDefault(lendReturn.getId(), Collections.emptyList());
            // 每期还款本金
            lendReturn.setPrincipal(lendItemReturnByLendIdList
                    .stream()
                    .map(LendItemReturn::getPrincipal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            // 每期还款利息
            lendReturn.setInterest(lendItemReturnByLendIdList
                    .stream()
                    .map(LendItemReturn::getInterest)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            // 每期还款本息
            lendReturn.setTotal(lendItemReturnByLendIdList
                    .stream()
                    .map(LendItemReturn::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        });
        lendReturnService.updateBatchById(lendReturnList);
    }

    /**
     * 回款计划
     *
     * @param lendReturnMap 还款期数与还款计划id对应map
     * @param lendItemId    投资标的 ID
     * @param lend          标的信息
     * @return {@link List}<{@link LendItemReturn}>
     */
    public List<LendItemReturn> returnInvest(Long lendItemId, Map<Integer, Long> lendReturnMap, Lend lend) {
        // 投标信息
        final LendItem lendItem = lendItemService.getById(lendItemId);

        // 投资金额
        final BigDecimal amount = lendItem.getInvestAmount();
        // 年化利率
        final BigDecimal yearRate = lendItem.getLendYearRate();
        // 投资期数
        final Integer period = lend.getPeriod();

        // 根据还款方式计算本金和利息
        // 还款期数 -> 利息
        Map<Integer, BigDecimal> periodInterest = null;
        // 还款期数 -> 本金
        Map<Integer, BigDecimal> periodPrincipal = null;
        // 1-等额本息
        if (lend
                .getReturnMethod()
                .intValue() == ReturnMethodEnum.ONE.getMethod()) {
            periodInterest = Amount1Helper.getPerMonthInterest(amount, yearRate, period);
            periodPrincipal = Amount1Helper.getPerMonthPrincipal(amount, yearRate, period);
        }
        // 2-等额本金
        else if (lend
                .getReturnMethod()
                .intValue() == ReturnMethodEnum.TWO.getMethod()) {
            periodInterest = Amount2Helper.getPerMonthInterest(amount, yearRate, period);
            periodPrincipal = Amount2Helper.getPerMonthPrincipal(amount, yearRate, period);
        }
        // 3-每月还息一次还本
        else if (lend
                .getReturnMethod()
                .intValue() == ReturnMethodEnum.THREE.getMethod()) {
            periodInterest = Amount3Helper.getPerMonthInterest(amount, yearRate, period);
            periodPrincipal = Amount3Helper.getPerMonthPrincipal(amount, yearRate, period);
        }
        // 4-一次还本还息
        else if (lend
                .getReturnMethod()
                .intValue() == ReturnMethodEnum.FOUR.getMethod()) {
            periodInterest = Amount4Helper.getPerMonthInterest(amount, yearRate, period);
            periodPrincipal = Amount4Helper.getPerMonthPrincipal(amount, yearRate, period);
        }

        //创建回款计划列表
        List<LendItemReturn> lendItemReturnList = new ArrayList<>();
        final Map<Integer, BigDecimal> finalPeriodPrincipal = periodPrincipal;
        final Map<Integer, BigDecimal> finalPeriodInterest = periodInterest;
        for (Integer currentPeriod : periodInterest.keySet()) {
            // 创建回款计划记录
            LendItemReturn lendItemReturn = new LendItemReturn();
            // 标的还款id
            lendItemReturn.setLendReturnId(lendReturnMap.get(currentPeriod));
            // 标的项id
            lendItemReturn.setLendItemId(lendItemId);
            // 标的id
            lendItemReturn.setLendId(lendItem.getLendId());
            // 出借用户id
            lendItemReturn.setInvestUserId(lendItem.getInvestUserId());
            // 出借金额
            lendItemReturn.setInvestAmount(lendItem.getInvestAmount());
            // 当前的期数
            lendItemReturn.setCurrentPeriod(currentPeriod);
            // 年化利率
            lendItemReturn.setLendYearRate(lendItem.getLendYearRate());
            // 还款方式
            lendItemReturn.setReturnMethod(lend.getReturnMethod());
            // 计算回款本金、利息和总额（注意最后一个月的计算）
            if (currentPeriod.intValue() == lend
                    .getPeriod()
                    .intValue()) {
                // 本金
                final BigDecimal sumPrincipal = lendItemReturnList
                        .stream()
                        .map(LendItemReturn::getPrincipal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                lendItemReturn.setPrincipal(lendItem
                        .getInvestAmount()
                        .subtract(sumPrincipal));
                // 利息
                final BigDecimal sumInterest = lendItemReturnList
                        .stream()
                        .map(LendItemReturn::getInterest)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                lendItemReturn.setInterest(lendItem
                        .getExpectAmount()
                        .subtract(sumInterest));
            }
            else {
                lendItemReturn.setPrincipal(finalPeriodPrincipal.get(currentPeriod));
                lendItemReturn.setInterest(finalPeriodInterest.get(currentPeriod));
            }
            // 本息
            lendItemReturn.setTotal(lendItemReturn
                    .getPrincipal()
                    .add(lendItemReturn.getInterest()));
            // 手续费
            lendItemReturn.setFee(BigDecimal.ZERO);
            // 还款时指定的还款日期
            lendItemReturn.setReturnDate(lend
                    .getLendStartDate()
                    .plusMonths(currentPeriod));
            // 是否逾期
            lendItemReturn.setOverdue(false);
            // 状态（0-未归还 1-已归还）
            lendItemReturn.setStatus(0);

            lendItemReturnList.add(lendItemReturn);
        }
        lendItemReturnService.saveBatch(lendItemReturnList);
        return lendItemReturnList;
    }
}
