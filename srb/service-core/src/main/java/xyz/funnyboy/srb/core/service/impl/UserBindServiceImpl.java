package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.UserBindMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserBind;
import xyz.funnyboy.srb.core.service.UserBindService;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService
{

}
