package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.TransFlowMapper;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.TransFlow;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.service.TransFlowService;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class TransFlowServiceImpl extends ServiceImpl<TransFlowMapper, TransFlow> implements TransFlowService
{
    @Resource
    private UserInfoService userInfoService;

    /**
     * 保存交易流水
     *
     * @param transFlowBO 交易流水BO
     */
    @Override
    public void saveTransFlow(TransFlowBO transFlowBO) {
        // 获取用户信息
        final UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getBindCode, transFlowBO.getBindCode()));

        // 保存交易流水
        final TransFlow transFlow = new TransFlow();
        transFlow.setUserId(userInfo.getId());
        transFlow.setUserName(userInfo.getName());
        transFlow.setTransNo(transFlowBO.getAgentBillNo());
        transFlow.setTransType(transFlowBO
                .getTransTypeEnum()
                .getTransType());
        transFlow.setTransTypeName(transFlowBO
                .getTransTypeEnum()
                .getTransTypeName());
        transFlow.setTransAmount(transFlowBO.getAmount());
        transFlow.setMemo(transFlowBO.getMemo());
        baseMapper.insert(transFlow);
    }

    /**
     * 判断流水是否存在
     *
     * @param agentBillNo 交易流水编号
     * @return boolean
     */
    @Override
    public boolean isSaveTransFlow(String agentBillNo) {
        return baseMapper.selectCount(new LambdaQueryWrapper<TransFlow>().eq(TransFlow::getTransNo, agentBillNo)) > 0;
    }
}
