package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.mapper.UserInfoMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.vo.RegisterVO;
import xyz.funnyboy.srb.core.service.UserInfoService;

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

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     */
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
    }
}
