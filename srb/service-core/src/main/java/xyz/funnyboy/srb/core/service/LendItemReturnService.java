package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.LendItemReturn;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface LendItemReturnService extends IService<LendItemReturn>
{

    /**
     * 根据标的ID获取回款列表
     *
     * @param lendId 标的 ID
     * @param userId 用户 ID
     * @return {@link List}<{@link LendItemReturn}>
     */
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);

    /**
     * 根据还款计划ID获取回款列表
     *
     * @param lendReturnId 还款计划 ID
     * @return {@link List}<{@link LendItemReturn}>
     */
    List<LendItemReturn> selectLendItemReturnList(Long lendReturnId);

    /**
     * 根据还款id获取回款列表
     *
     * @param lendReturnId 借出归还 ID
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    List<Map<String, Object>> addReturnDetail(Long lendReturnId);
}
