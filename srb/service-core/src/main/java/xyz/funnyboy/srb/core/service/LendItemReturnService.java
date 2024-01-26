package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.LendItemReturn;

import java.util.List;

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
     * 根据标的ID获取回款计划     *
     *
     * @param lendId 标的 ID
     * @param userId 用户 ID
     * @return {@link List}<{@link LendItemReturn}>
     */
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);
}
