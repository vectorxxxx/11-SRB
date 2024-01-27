package xyz.funnyboy.srb.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.enums.LendStatusEnum;
import xyz.funnyboy.srb.core.enums.TransTypeEnum;
import xyz.funnyboy.srb.core.hfb.FormHelper;
import xyz.funnyboy.srb.core.hfb.HfbConst;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.mapper.LendItemMapper;
import xyz.funnyboy.srb.core.mapper.UserAccountMapper;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.pojo.vo.InvestVO;
import xyz.funnyboy.srb.core.service.*;
import xyz.funnyboy.srb.core.utils.LendNoUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
@Slf4j
public class LendItemServiceImpl extends ServiceImpl<LendItemMapper, LendItem> implements LendItemService
{

    @Resource
    private LendService lendService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserBindService userBindService;

    @Resource
    private TransFlowService transFlowService;

    /**
     * 提交投资
     *
     * @param investVO 投资VO
     * @return {@link String}
     */
    @Override
    public String commitInvest(InvestVO investVO) {
        // 校验==========================================
        // 1、标的必须募资中
        final Long lendId = investVO.getLendId();
        final Lend lend = lendService.getById(lendId);
        Assert.isTrue(lend
                .getStatus()
                .intValue() == LendStatusEnum.INVEST_RUN
                .getStatus()
                .intValue(), ResponseEnum.LEND_INVEST_ERROR);
        // 2、标的不能超卖：(已投金额 + 本次投资金额 )>=标的金额（超卖）
        final BigDecimal sum = lend
                .getInvestAmount()
                .add(BigDecimal.valueOf(Long.parseLong(investVO.getInvestAmount())));
        Assert.isTrue(sum.compareTo(lend.getAmount()) <= 0, ResponseEnum.LEND_FULL_SCALE_ERROR);
        // 3、账户可用余额充足
        final Long investUserId = investVO.getInvestUserId();
        final BigDecimal amount = userAccountService.getAccountAmount(investUserId);
        Assert.isTrue(amount.compareTo(BigDecimal.valueOf(Long.parseLong(investVO.getInvestAmount()))) >= 0, ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        // 在商户平台中生成投资信息==========================================
        // 标的下的投资信息
        LendItem lendItem = new LendItem();
        // 投资用户id
        lendItem.setInvestUserId(investUserId);
        // 投资人名称
        lendItem.setInvestName(investVO.getInvestName());
        // 投资编号
        final String lendItemNo = LendNoUtils.getLendItemNo();
        lendItem.setLendItemNo(lendItemNo);
        // 标的id
        lendItem.setLendId(lendId);
        // 投资金额
        lendItem.setInvestAmount(BigDecimal.valueOf(Long.parseLong(investVO.getInvestAmount())));
        // 年化利率
        lendItem.setLendYearRate(lend.getLendYearRate());
        // 投资时间
        lendItem.setInvestTime(LocalDateTime.now());
        // 开始日期
        lendItem.setLendStartDate(lend.getLendStartDate());
        // 结束日期
        lendItem.setLendEndDate(lend.getLendEndDate());
        // 预期收益
        lendItem.setExpectAmount(lendService.getInterestCount(lendItem.getInvestAmount(), lend.getLendYearRate(), lend.getPeriod(), lend.getReturnMethod()));
        // 实际收益
        lendItem.setRealAmount(BigDecimal.ZERO);
        // 状态
        lendItem.setStatus(0);
        baseMapper.insert(lendItem);

        // 组装投资相关的参数，提交到汇付宝资金托管平台==========================================
        // 在托管平台同步用户的投资信息，修改用户的账户资金信息==========================================
        // 获取投资人的绑定协议号
        String bindCode = userBindService.getBindCodeByUserId(investUserId);
        // 获取借款人的绑定协议号
        String benefitBindCode = userBindService.getBindCodeByUserId(lend.getUserId());

        // 封装提交至汇付宝的参数
        Map<String, Object> paramMap = new HashMap<>();
        // agent_id	int		是	给商户分配的唯一标识
        paramMap.put("agentId", HfbConst.AGENT_ID);
        // vote_bind_code	string	50	是	投资人绑定协议号
        paramMap.put("voteBindCode", bindCode);
        // benefit_bind_code	string	50	是	借款人绑定协议号。
        //          同一个项目只能有一个借款人。
        paramMap.put("benefitBindCode", benefitBindCode);
        // agent_project_code	string	50	是	项目编号。只能由数字、字母组成。
        paramMap.put("agentProjectCode", lend.getLendNo());
        // agent_project_name	string	100	是	项目名称。
        paramMap.put("agentProjectName", lend.getTitle());
        // agent_bill_no	string	50	是	商户订单号。只能由数字、字母组成，1~50字符。
        //          同一商户，所有订单号（投资单号、放款单号、转账单号）必须唯一。
        paramMap.put("agentBillNo", lendItemNo);
        // vote_amt	decimal		是	投资金额。最多小数点后两位，必须大于0。
        paramMap.put("voteAmt", investVO.getInvestAmount());
        // vote_prize_amt	decimal		否	投资奖励金额。
        //          该金额由商户出资奖励投资人，同时该资金当作投资金额。比如：vote_amt=100, vote_prize_amt=1，则本次投资总额=100+1。
        //          撤标时，该金额返给商户。
        paramMap.put("votePrizeAmt", BigDecimal.ZERO);
        // vote_fee_amt	decimal		是	商户手续费。最多小数点后两位，不能小于0。
        paramMap.put("voteFeeAmt", BigDecimal.ZERO);
        // project_amt	decimal		否	项目总金额。用来控制项目投标总金额。不传或传0，汇付宝均不控制项目投标总金额。
        paramMap.put("projectAmt", lend.getAmount());
        // note	string	255	否	投资备注。
        paramMap.put("note", "投资");
        // notify_url	string	255	是	接收投资信息完成后，异步通商户投资处理结果的完整地址
        paramMap.put("notifyUrl", HfbConst.INVEST_NOTIFY_URL);
        // return_url	string	255	是	接收投资信息完成后，同步返回商户的完整地址
        paramMap.put("returnUrl", HfbConst.INVEST_RETURN_URL);
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        // sign	string	32	是	验签参数。
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        log.info("投资请求参数：{}", JSON.toJSONString(paramMap));
        final String formStr = FormHelper.buildForm(HfbConst.INVEST_URL, paramMap);
        log.info("投资请求结果：{}", formStr);
        return formStr;
    }

    /**
     * 会员投资异步回调
     *
     * @param paramMap 参数映射
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void notify(Map<String, Object> paramMap) {
        // 幂等性返回
        final String agentBillNo = (String) paramMap.get("agentBillNo");
        final boolean saveTransFlow = transFlowService.isSaveTransFlow(agentBillNo);
        if (saveTransFlow) {
            log.warn("幂等性返回");
            return;
        }

        // 获取用户的绑定协议号
        final String bindCode = (String) paramMap.get("voteBindCode");
        final String voteAmt = (String) paramMap.get("voteAmt");

        // 修改商户系统中的用户账户金额：余额、冻结金额
        userAccountMapper.updateAccount(bindCode, new BigDecimal("-" + voteAmt), new BigDecimal(voteAmt));

        // 修改投资记录的投资状态改为已支付
        LendItem lendItem = this.getByLendItemNo(agentBillNo);
        lendItem.setStatus(1);
        baseMapper.updateById(lendItem);

        // 修改标的信息：投资人数、已投金额
        final Lend lend = lendService.getById(lendItem.getLendId());
        lend.setInvestNum(lend.getInvestNum() + 1);
        final BigDecimal amount = BigDecimal.valueOf(Long.parseLong(voteAmt));
        lend.setInvestAmount(lend
                .getInvestAmount()
                .add(amount));
        lendService.updateById(lend);

        // 新增交易流水
        final String memo = String.format("投资项目编号：%s，项目名称：%s", lend.getLendNo(), lend.getTitle());
        transFlowService.saveTransFlow(new TransFlowBO(agentBillNo, bindCode, amount, TransTypeEnum.INVEST_LOCK, memo));
    }

    /**
     * 获取投资列表信息
     *
     * @param lendId 标的 ID
     * @param status 状态
     * @return {@link List}<{@link LendItem}>
     */
    @Override
    public List<LendItem> selectByLendId(Long lendId, Integer status) {
        return baseMapper.selectList(new LambdaQueryWrapper<LendItem>()
                .eq(LendItem::getLendId, lendId)
                .eq(LendItem::getStatus, status));
    }

    /**
     * 获取投资列表信息
     *
     * @param lendId 借出 ID
     * @return {@link List}<{@link LendItem}>
     */
    @Override
    public List<LendItem> selectByLendId(Long lendId) {
        return baseMapper.selectList(new LambdaQueryWrapper<LendItem>().eq(LendItem::getLendId, lendId));
    }

    /**
     * 通过投资记录No获取标的信息
     *
     * @param lendItemNo 借出物品编号
     * @return {@link LendItem}
     */
    private LendItem getByLendItemNo(String lendItemNo) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LendItem>().eq(LendItem::getLendItemNo, lendItemNo));
    }
}
