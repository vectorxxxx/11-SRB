package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.LendItemReturnMapper;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.pojo.entity.LendItemReturn;
import xyz.funnyboy.srb.core.pojo.entity.LendReturn;
import xyz.funnyboy.srb.core.service.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 标的出借回款记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class LendItemReturnServiceImpl extends ServiceImpl<LendItemReturnMapper, LendItemReturn> implements LendItemReturnService
{
    @Resource
    private UserBindService userBindService;

    @Resource
    private LendService lendService;

    @Resource
    private LendItemService lendItemService;

    @Resource
    private LendReturnService lendReturnService;

    /**
     * 根据标的ID获取回款计划     *
     *
     * @param lendId 标的 ID
     * @param userId 用户 ID
     * @return {@link List}<{@link LendItemReturn}>
     */
    @Override
    public List<LendItemReturn> selectByLendId(Long lendId, Long userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<LendItemReturn>()
                .eq(LendItemReturn::getLendId, lendId)
                .eq(LendItemReturn::getInvestUserId, userId));
    }

    /**
     * 根据还款计划ID获取回款列表
     *
     * @param lendReturnId 还款计划 ID
     * @return {@link List}<{@link LendItemReturn}>
     */
    @Override
    public List<LendItemReturn> selectLendItemReturnList(Long lendReturnId) {
        return baseMapper.selectList(new LambdaQueryWrapper<LendItemReturn>().eq(LendItemReturn::getLendReturnId, lendReturnId));
    }

    /**
     * 根据还款id获取还款明细
     *
     * @param lendReturnId 借出归还 ID
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public List<Map<String, Object>> addReturnDetail(Long lendReturnId) {
        // 还款计划
        final LendReturn lendReturn = lendReturnService.getById(lendReturnId);
        // 标的信息
        final Lend lend = lendService.getById(lendReturn.getLendId());

        // 返回还款明细
        return this
                // 根据还款计划ID获取回款列表
                .selectLendItemReturnList(lendReturnId)
                .stream()
                // 转换为还款明细
                .map(lendItemReturn -> {
                    // 投资信息
                    final LendItem lendItem = lendItemService.getById(lendItemReturn.getLendItemId());
                    // 投资人绑定协议号
                    final String bindCode = userBindService.getBindCodeByUserId(lendItem.getInvestUserId());

                    Map<String, Object> map = new HashMap<>();
                    //      agent_project_code：还款项目编号。
                    map.put("agentProjectCode", lend.getLendNo());
                    //      vote_bill_no：投资单号
                    map.put("voteBillNo", lendItem.getLendItemNo());
                    //      to_bind_code：收款人（投资人）
                    map.put("toBindCode", bindCode);
                    //      transit_amt：还款金额 (必须等于base_amt+benifit_amt), 最多支持小数点后2位
                    map.put("transitAmt", lendItemReturn.getTotal());
                    //      base_amt：还款本金。最多小数点后2位
                    map.put("baseAmt", lendItemReturn.getPrincipal());
                    //      benifit_amt：还款利息(如果base_amt、benifit_amt都大于0，则benifit_amt    不能大于base_amt的50%，且该投资单所有还款利息不得超过投资金额的50%),最多小数点后2位。base_amt、benifit_amt至少有一项必须大于0
                    map.put("benifitAmt", lendItemReturn.getInterest());
                    //      fee_amt：商户手续费。最多小数点后2位
                    map.put("feeAmt", lendItemReturn.getFee());
                    return map;
                })
                .collect(Collectors.toList());
    }

}
