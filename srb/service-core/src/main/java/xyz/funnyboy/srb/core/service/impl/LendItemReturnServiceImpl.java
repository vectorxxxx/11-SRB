package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.LendItemReturnMapper;
import xyz.funnyboy.srb.core.pojo.entity.LendItemReturn;
import xyz.funnyboy.srb.core.service.LendItemReturnService;

import java.util.List;

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
}
