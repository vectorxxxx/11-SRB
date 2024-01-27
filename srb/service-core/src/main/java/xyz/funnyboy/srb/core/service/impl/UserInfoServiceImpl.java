package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.mapper.UserInfoMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.entity.UserLoginRecord;
import xyz.funnyboy.srb.core.pojo.query.UserInfoQuery;
import xyz.funnyboy.srb.core.pojo.vo.LoginVO;
import xyz.funnyboy.srb.core.pojo.vo.RegisterVO;
import xyz.funnyboy.srb.core.pojo.vo.UserIndexVO;
import xyz.funnyboy.srb.core.pojo.vo.UserInfoVO;
import xyz.funnyboy.srb.core.service.UserAccountService;
import xyz.funnyboy.srb.core.service.UserInfoService;
import xyz.funnyboy.srb.core.service.UserLoginRecordService;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService
{
    @Resource
    private UserAccountService userAccountService;

    @Resource
    private UserLoginRecordService userLoginRecordService;

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterVO registerVO) {
        // 是否已注册
        final Integer count = baseMapper.selectCount(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getMobile, registerVO.getMobile()));
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        // 保存用户基本信息
        final UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(registerVO, userInfo);
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg("https://srb-vectorx.oss-cn-shanghai.aliyuncs.com/avatar/1.gif");
        baseMapper.insert(userInfo);

        // 创建会员账户
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountService.save(userAccount);
    }

    /**
     * 登录
     *
     * @param loginVO 登录 VO
     * @param ip      ip
     * @return {@link UserInfoVO}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfoVO login(LoginVO loginVO, String ip) {
        // 查询用户信息
        final UserInfo userInfo = baseMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getMobile, loginVO.getMobile())
                .eq(UserInfo::getUserType, loginVO.getUserType()));
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);

        // 校验密码
        Assert.equals(userInfo.getPassword(), loginVO.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);

        // 校验禁用状态
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecord.setIp(ip);
        userLoginRecordService.save(userLoginRecord);

        // 返回用户信息
        final UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        userInfoVO.setToken(JwtUtils.createToken(userInfo.getId(), userInfo.getNickName()));
        return userInfoVO;
    }

    /**
     * 列表页
     *
     * @param pageParam     页面参数
     * @param userInfoQuery 查询参数
     * @return {@link IPage}<{@link UserInfo}>
     */
    @Override
    public IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery) {
        // 无查询条件
        if (userInfoQuery == null) {
            return baseMapper.selectPage(pageParam, null);
        }

        // 有查询条件
        final LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(StringUtils.isNotBlank(userInfoQuery.getMobile()), UserInfo::getMobile, userInfoQuery.getMobile())
                .eq(userInfoQuery.getStatus() != null, UserInfo::getStatus, userInfoQuery.getStatus())
                .eq(userInfoQuery.getUserType() != null, UserInfo::getUserType, userInfoQuery.getUserType());
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 锁定用户
     *
     * @param id     编号
     * @param status 状态
     */
    @Override
    public void lock(Long id, Integer status) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }

    /**
     * 校验手机号是否注册
     *
     * @param mobile 移动
     * @return boolean
     */
    @Override
    public boolean checkMobile(String mobile) {
        return baseMapper.selectCount(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getMobile, mobile)) > 0;
    }

    /**
     * 获取个人空间用户信息
     *
     * @param userId 用户 ID
     * @return {@link UserIndexVO}
     */
    @Override
    public UserIndexVO getIndexUserInfo(Long userId) {
        // 用户信息
        final UserInfo userInfo = baseMapper.selectById(userId);

        // 账户信息
        final UserAccount userAccount = userAccountService.getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId, userId));

        // 登录信息
        final UserLoginRecord userLoginRecord = userLoginRecordService.getOne(new LambdaQueryWrapper<UserLoginRecord>()
                .eq(UserLoginRecord::getUserId, userId)
                .orderByDesc(UserLoginRecord::getId)
                .last("limit 1"));

        // 返回结果
        final UserIndexVO indexVO = new UserIndexVO();
        indexVO.setUserId(userId);
        indexVO.setName(userInfo.getName());
        indexVO.setNickName(userInfo.getNickName());
        indexVO.setUserType(userInfo.getUserType());
        indexVO.setHeadImg(userInfo.getHeadImg());
        indexVO.setBindStatus(userInfo.getBindStatus());
        indexVO.setAmount(userAccount.getAmount());
        indexVO.setFreezeAmount(userAccount.getFreezeAmount());
        indexVO.setLastLoginTime(userLoginRecord.getCreateTime());
        return indexVO;
    }
}
