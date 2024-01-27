package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.bo.TransFlowBO;
import xyz.funnyboy.srb.core.pojo.entity.TransFlow;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface TransFlowService extends IService<TransFlow>
{
    /**
     * 保存交易流水
     *
     * @param transFlowBO 交易流水BO
     */
    void saveTransFlow(TransFlowBO transFlowBO);

    /**
     * 判断流水是否存在
     *
     * @param agentBillNo 交易流水编号
     * @return boolean
     */
    boolean isSaveTransFlow(String agentBillNo);

    /**
     * 按用户ID查询
     *
     * @param userId 用户 ID
     * @return {@link List}<{@link TransFlow}>
     */
    List<TransFlow> selectByUserId(Long userId);
}
