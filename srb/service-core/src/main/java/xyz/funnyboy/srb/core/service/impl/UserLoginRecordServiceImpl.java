package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.UserLoginRecordMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserLoginRecord;
import xyz.funnyboy.srb.core.service.UserLoginRecordService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService
{

    /**
     * 列出 top50
     *
     * @param userId 用户 ID
     * @return {@link List}<{@link UserLoginRecord}>
     */
    @Override
    public List<UserLoginRecord> listTop50(Long userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<UserLoginRecord>()
                .eq(UserLoginRecord::getUserId, userId)
                .orderByDesc(UserLoginRecord::getId)
                .last("limit 50"));
    }
}
