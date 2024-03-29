package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.query.UserInfoQuery;
import xyz.funnyboy.srb.core.pojo.vo.LoginVO;
import xyz.funnyboy.srb.core.pojo.vo.RegisterVO;
import xyz.funnyboy.srb.core.pojo.vo.UserIndexVO;
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

    /**
     * 登录
     *
     * @param loginVO 登录 VO
     * @param ip      ip
     * @return {@link UserInfoVO}
     */
    UserInfoVO login(LoginVO loginVO, String ip);

    /**
     * 列表页
     *
     * @param pageParam     页面参数
     * @param userInfoQuery 查询参数
     * @return {@link IPage}<{@link UserInfo}>
     */
    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    /**
     * 锁定用户
     *
     * @param id     编号
     * @param status 状态
     */
    void lock(Long id, Integer status);

    /**
     * 校验手机号是否注册
     *
     * @param mobile 移动
     * @return boolean
     */
    boolean checkMobile(String mobile);

    /**
     * 获取个人空间用户信息
     *
     * @param userId 用户 ID
     * @return {@link UserIndexVO}
     */
    UserIndexVO getIndexUserInfo(Long userId);

    /**
     * 通过绑定代码获取手机号
     *
     * @param bindCode 绑定代码
     * @return {@link String}
     */
    String getMobileByBindCode(String bindCode);
}
