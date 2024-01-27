package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.enums.TransTypeEnum;
import xyz.funnyboy.srb.core.hfb.FormHelper;
import xyz.funnyboy.srb.core.hfb.HfbConst;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.mapper.UserAccountMapper;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.service.TransFlowService;
import xyz.funnyboy.srb.core.service.UserAccountService;
import xyz.funnyboy.srb.core.service.UserBindService;
import xyz.funnyboy.srb.core.service.UserInfoService;
import xyz.funnyboy.srb.core.utils.LendNoUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService
{
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private UserBindService userBindService;

    /**
     * 充值
     *
     * @param chargeAmt 充电 AMT
     * @param userId    用户 ID
     * @return {@link String}
     */
    @Override
    public String commitCharge(Long chargeAmt, Long userId) {
        final UserInfo userInfo = userInfoService.getById(userId);
        final String bindCode = userInfo.getBindCode();
        Assert.notEmpty(bindCode, ResponseEnum.USER_NO_BIND_ERROR);

        // agent_id	int		是	给商户分配的唯一标识
        // agent_bill_no	string	50	是	商户充值单号（要求唯一）
        // bind_code	string	50	是	充值人绑定协议号。
        // charge_amt	decimal		是	充值金额，即充值到汇付宝的金额。支持小数点后2位。
        // fee_amt	decimal		是	商户收取用户的手续费。支持小数点后2位。可以传0。
        // 注意：从银行卡扣除金额= charge_amt+ fee_amt，用户汇付宝余额增加charge_amt，fee_amt转到商户账户。
        // notify_url	string	255	是	通知商户充值成功的完整地址
        // return_url	string	255	否	充值完成后同步返回商户的完整地址。
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        // sign	string	32	是	验签参数。agent_id	int		是	给商户分配的唯一标识
        Map<String, Object> paramMap = new java.util.HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", BigDecimal.ZERO);
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        final String formStr = FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
        log.info("充值表单：" + formStr);
        return formStr;
    }

    /**
     * 用户充值异步回调
     *
     * @param paramMap 参数映射
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void notify(Map<String, Object> paramMap) {
        // 判断交易流水是否存在
        final String agentBillNo = (String) paramMap.get("agentBillNo");
        final boolean saveTransFlow = transFlowService.isSaveTransFlow(agentBillNo);
        if (saveTransFlow) {
            log.warn("幂等性返回");
            return;
        }

        // bind_code	string	50	是	充值人绑定协议号。
        final String bindCode = (String) paramMap.get("bindCode");
        // charge_amt	decimal		是	充值金额
        final String chargeAmt = (String) paramMap.get("chargeAmt");

        // 更新会员账户余额
        final BigDecimal amount = new BigDecimal(chargeAmt);
        baseMapper.updateAccount(bindCode, amount, BigDecimal.ZERO);

        // 增加交易流水
        transFlowService.saveTransFlow(new TransFlowBO(agentBillNo, bindCode, amount, TransTypeEnum.RECHARGE, "重置"));
    }

    /**
     * 查询账户余额
     *
     * @param userId 用户 ID
     * @return {@link BigDecimal}
     */
    @Override
    public BigDecimal getAccountAmount(Long userId) {
        // 根据userId查找用户账户
        final UserAccount userAccount = baseMapper.selectOne(new LambdaQueryWrapper<UserAccount>()
                .select(UserAccount::getAmount)
                .eq(UserAccount::getUserId, userId));
        return userAccount.getAmount();
    }

    /**
     * 用户提现
     *
     * @param fetchAmt 获取 AMT
     * @param userId   用户 ID
     * @return {@link String}
     */
    @Override
    public String commitWithdraw(Long fetchAmt, Long userId) {
        // 账户可用余额充足：当前用户的余额 >= 当前用户的提现金额
        final BigDecimal amount = this.getAccountAmount(userId);
        Assert.isTrue(amount.compareTo(BigDecimal.valueOf(fetchAmt)) >= 0, ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        // 查询绑定账户协议号
        final String bindCode = userBindService.getBindCodeByUserId(userId);

        Map<String, Object> paramMap = new HashMap<>();
        // agent_id	int		是	给商户分配的唯一标识
        paramMap.put("agentId", HfbConst.AGENT_ID);
        // agent_bill_no	string	50	是	商户提现单号。只能由数字、字母组成，12~50个字符。
        paramMap.put("agentBillNo", LendNoUtils.getWithdrawNo());
        // bind_code	string	50	是	用户绑定协议号。只能由数字、字母组成，12~50个字符。
        paramMap.put("bindCode", bindCode);
        // fetch_amt	decimal		是	提现金额。必须大于0。最多支持小数点后两位。
        paramMap.put("fetchAmt", fetchAmt);
        // fee_amt	decimal		是	商户收取用户的手续费。不能小于0。最多支持小数点后两位。
        paramMap.put("feeAmt", BigDecimal.ZERO);
        // return_url	string	100	是	用户提现申请成功后，同步返回到商户的完整url。
        paramMap.put("returnUrl", HfbConst.WITHDRAW_RETURN_URL);
        // notify_url	string	500	是	通知商户提现的完整url。
        paramMap.put("notifyUrl", HfbConst.WITHDRAW_NOTIFY_URL);
        // 用户提现申请成功、提现处理结果都通知给该地址。
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        // sign	string	32	是	验签参数。
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        // 构建提现表单
        final String formStr = FormHelper.buildForm(HfbConst.WITHDRAW_URL, paramMap);
        log.info("提现表单：" + formStr);
        return formStr;
    }

    /**
     * 用户提现异步回调
     *
     * @param paramMap 参数映射
     */
    @Override
    public void notifyWithdraw(Map<String, Object> paramMap) {
        // agent_bill_no	string	50	是	商户提现单号（与商户传过来的一致）
        // bind_code	string	50	是	充值人绑定协议号。
        // fetch_amt	decimal		是	提现金额（与商户传过来的一致）
        // fee_amt	decimal		是	商户收取用户的手续费（与商户传过来的一致）
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        // sign	string	32	是	验签参数。
        final String agentBillNo = (String) paramMap.get("agentBillNo");
        final boolean saveTransFlow = transFlowService.isSaveTransFlow(agentBillNo);
        if (saveTransFlow) {
            log.warn("幂等性返回");
            return;
        }

        final String bindCode = (String) paramMap.get("bindCode");
        final String fetchAmt = (String) paramMap.get("fetchAmt");

        // 根据用户账户修改账户金额
        baseMapper.updateAccount(bindCode, new BigDecimal("-" + fetchAmt), BigDecimal.ZERO);

        // 增加交易流水
        transFlowService.saveTransFlow(new TransFlowBO(agentBillNo, bindCode, BigDecimal.valueOf(Double.parseDouble(fetchAmt)), TransTypeEnum.WITHDRAW, "提现"));
    }
}
