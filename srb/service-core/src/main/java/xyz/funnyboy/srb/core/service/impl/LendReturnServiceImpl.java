package xyz.funnyboy.srb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.enums.LendStatusEnum;
import xyz.funnyboy.srb.core.enums.TransTypeEnum;
import xyz.funnyboy.srb.core.hfb.FormHelper;
import xyz.funnyboy.srb.core.hfb.HfbConst;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.mapper.LendReturnMapper;
import xyz.funnyboy.srb.core.mapper.UserAccountMapper;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.pojo.entity.LendReturn;
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
 * 还款记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
@Slf4j
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService
{
    @Resource
    private UserAccountService userAccountService;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserBindService userBindService;

    @Resource
    private LendService lendService;

    @Resource
    private LendItemService lendItemService;

    @Resource
    private LendItemReturnService lendItemReturnService;

    @Resource
    private TransFlowService transFlowService;

    /**
     * 根据标的 ID 查询还款记录
     *
     * @param lendId 借出 ID
     * @return {@link List}<{@link LendReturn}>
     */
    @Override
    public List<LendReturn> selectByLendId(Long lendId) {
        return baseMapper.selectList(new LambdaQueryWrapper<LendReturn>().eq(LendReturn::getLendId, lendId));
    }

    /**
     * 用户还款
     *
     * @param lendReturnId 还款 ID
     * @param userId       用户 ID
     * @return {@link String}
     */
    @Override
    public String commitReturn(Long lendReturnId, Long userId) {
        // 还款计划
        final LendReturn lendReturn = baseMapper.selectById(lendReturnId);
        // 余额是否充足
        final BigDecimal amount = userAccountService.getAccountAmount(userId);
        Assert.isTrue(amount.compareTo(lendReturn.getTotal()) >= 0, ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        // 标的信息
        final Lend lend = lendService.getById(lendReturn.getLendId());
        // 还款人绑定协议号
        final String bindCode = userBindService.getBindCodeByUserId(userId);
        // 还款明细
        final List<Map<String, Object>> returnDetail = lendItemReturnService.addReturnDetail(lendReturnId);

        Map<String, Object> paramMap = new HashMap<>();
        // agent_id	int		是	给商户分配的唯一标识
        paramMap.put("agentId", HfbConst.AGENT_ID);
        // agent_goods_name	string	100	是	商户商品名称。
        paramMap.put("agentGoodsName", lend.getTitle());
        // agent_batch_no	string	50	是	批次号。数字、字母组成
        paramMap.put("agentBatchNo", lendReturn.getReturnNo());
        // from_bind_code	string	50	是	还款人绑定协议号
        paramMap.put("fromBindCode", bindCode);
        // total_amt	decimal		是	还款总额
        paramMap.put("totalAmt", lendReturn.getTotal());
        // note	string	255	否	备注
        paramMap.put("note", "用户" + userId + "还款");
        // data	string		是	还款明细数据，json格式。目前一次最多支持100个单。
        paramMap.put("data", JSONObject.toJSONString(returnDetail));
        // vote_fee_amt	decimal		是	商户手续费。最多小数点后两位，不能小于0。
        paramMap.put("voteFeeAmt", lendReturn.getFee());
        // return_url	string	255	是	还款扣款完成后，扣款结果同步返回到商户的完整地址
        paramMap.put("returnUrl", HfbConst.BORROW_RETURN_RETURN_URL);
        // notify_url	string	255	是	换款扣款完成后，异步通知商户扣款结果
        paramMap.put("notifyUrl", HfbConst.BORROW_RETURN_NOTIFY_URL);
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数。
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        // sign	string	32	是	验签参数。
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        // 生成表单
        final String formStr = FormHelper.buildForm(HfbConst.BORROW_RETURN_URL, paramMap);
        log.info("formStr:{}", formStr);
        return formStr;
    }

    /**
     * 用户还款异步回调
     *
     * @param paramMap 参数映射
     */
    @Override
    public void notify(Map<String, Object> paramMap) {
        // agent_batch_no	string	50	是	批次号
        final String agentBatchNo = (String) paramMap.get("agentBatchNo");
        // total_amt	decimal		是	扣款额
        final BigDecimal totalAmt = BigDecimal.valueOf(Double.parseDouble((String) paramMap.get("totalAmt")));
        // vote_fee_amt	decimal		是	商户手续费。最多小数点后两位，不能小于0。
        final BigDecimal voteFeeAmt = BigDecimal.valueOf(Double.parseDouble((String) paramMap.get("voteFeeAmt")));

        // 判断是否重复提交
        if (transFlowService.isSaveTransFlow(agentBatchNo)) {
            log.warn("幂等性返回");
            return;
        }

        // 获取还款计划
        final LendReturn lendReturn = baseMapper.selectOne(new LambdaQueryWrapper<LendReturn>().eq(LendReturn::getReturnNo, agentBatchNo));

        // 更新还款状态
        // 状态（0-未归还 1-已归还）
        lendReturn.setStatus(1);
        lendReturn.setFee(voteFeeAmt);
        lendReturn.setRealReturnTime(LocalDateTime.now());
        baseMapper.updateById(lendReturn);

        // 更新标的信息
        final Lend lend = lendService.getById(lendReturn.getLendId());
        // 最后一次还款更新标的状态
        if (lendReturn.getLast()) {
            lend.setStatus(LendStatusEnum.PAY_OK.getStatus());
            lendService.updateById(lend);
        }

        // 借款账号转出金额
        // 获取借款人账号绑定协议号
        final String bindCode = userBindService.getBindCodeByUserId(lendReturn.getUserId());
        userAccountMapper.updateAccount(bindCode, totalAmt.negate(), BigDecimal.ZERO);

        // 增加交易流水号
        final String memo1 = String.format("借款人还款扣减，项目编号：%s，项目名称：%s", lend.getLendNo(), lend.getTitle());
        transFlowService.saveTransFlow(new TransFlowBO(agentBatchNo, bindCode, totalAmt, TransTypeEnum.RETURN_DOWN, memo1));

        // 更新回款信息
        lendItemReturnService
                // 查询回款明细
                .selectLendItemReturnList(lendReturn.getId())
                .forEach(lendItemReturn -> {
                    // 更新回款状态
                    // 状态（0-未归还 1-已归还）
                    lendItemReturn.setStatus(1);
                    lendItemReturn.setRealReturnTime(LocalDateTime.now());
                    lendItemReturnService.updateById(lendItemReturn);

                    // 更新投资信息
                    final LendItem lendItem = lendItemService.getById(lendItemReturn.getLendItemId());
                    // 实际收益
                    lendItem.setRealAmount(lendItemReturn.getInterest());
                    lendItemService.updateById(lendItem);

                    // 投资账号转入金额
                    final String investBindCode = userBindService.getBindCodeByUserId(lendItem.getInvestUserId());
                    userAccountMapper.updateAccount(investBindCode, totalAmt, BigDecimal.ZERO);

                    // 投资账号交易流水
                    final String memo2 = String.format("还款到账，项目编号：%s，项目名称：%s", lend.getLendNo(), lend.getTitle());
                    transFlowService.saveTransFlow(new TransFlowBO(LendNoUtils.getReturnItemNo(), investBindCode, lendItemReturn.getTotal(), TransTypeEnum.INVEST_BACK, memo2));
                });
    }
}
