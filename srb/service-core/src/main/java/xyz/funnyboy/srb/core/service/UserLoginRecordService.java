package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.UserLoginRecord;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface UserLoginRecordService extends IService<UserLoginRecord>
{

    /**
     * 列出 top50
     *
     * @param userId 用户 ID
     * @return {@link List}<{@link UserLoginRecord}>
     */
    List<UserLoginRecord> listTop50(Long userId);
}
