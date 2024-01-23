package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.UserLoginRecordMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserLoginRecord;
import xyz.funnyboy.srb.core.service.UserLoginRecordService;

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

}
