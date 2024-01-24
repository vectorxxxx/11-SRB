package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.vo.LoginVO;
import xyz.funnyboy.srb.core.pojo.vo.RegisterVO;
import xyz.funnyboy.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface UserInfoService extends IService<UserInfo>
{

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     */
    void register(RegisterVO registerVO);

    UserInfoVO login(LoginVO loginVO, String ip);
}
