package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.LendReturn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface LendReturnService extends IService<LendReturn>
{

    /**
     * 根据标的 ID 查询还款计划
     *
     * @param lendId 借出 ID
     * @return {@link List}<{@link LendReturn}>
     */
    List<LendReturn> selectByLendId(Long lendId);

    /**
     * 用户还款
     *
     * @param lendReturnId 还款 ID
     * @param userId       用户 ID
     * @return {@link String}
     */
    String commitReturn(Long lendReturnId, Long userId);

    /**
     * 用户还款异步回调
     *
     * @param paramMap 参数映射
     */
    void notify(Map<String, Object> paramMap);
}
