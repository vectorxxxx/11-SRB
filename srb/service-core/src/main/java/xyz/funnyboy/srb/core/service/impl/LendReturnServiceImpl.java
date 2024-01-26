package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.LendReturnMapper;
import xyz.funnyboy.srb.core.pojo.entity.LendReturn;
import xyz.funnyboy.srb.core.service.LendReturnService;

import java.util.List;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService
{

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
}
