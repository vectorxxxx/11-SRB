package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.UserAccountMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;
import xyz.funnyboy.srb.core.service.UserAccountService;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService
{

}
