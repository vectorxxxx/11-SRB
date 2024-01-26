package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.enums.UserBindEnum;
import xyz.funnyboy.srb.core.hfb.FormHelper;
import xyz.funnyboy.srb.core.hfb.HfbConst;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.mapper.UserBindMapper;
import xyz.funnyboy.srb.core.pojo.entity.UserBind;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.vo.UserBindVO;
import xyz.funnyboy.srb.core.service.UserBindService;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
@Slf4j
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService
{

    @Resource
    private UserInfoService userInfoService;

    /**
     * 账户绑定提交到托管平台的数据
     *
     * @param userBindVO 用户绑定 VO
     * @param userId     用户 ID
     * @return {@link String}
     */
    @Override
    public String commitBindUser(UserBindVO userBindVO, Long userId) {
        // 校验身份证号码是否已经被别人绑定
        final Integer count = baseMapper.selectCount(new LambdaQueryWrapper<UserBind>()
                .eq(UserBind::getIdCard, userBindVO.getIdCard())
                .ne(UserBind::getUserId, userId));
        Assert.isTrue(count == 0, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);

        // 查询账户绑定信息
        UserBind userBind = baseMapper.selectOne(new LambdaQueryWrapper<UserBind>().eq(UserBind::getUserId, userId));
        if (userBind == null) {
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVO, userBind);
            userBind.setUserId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
            baseMapper.insert(userBind);
            log.info("创建用户并绑定账户成功");
        }
        else {
            BeanUtils.copyProperties(userBindVO, userBind);
            baseMapper.updateById(userBind);
            log.info("绑定账户成功");
        }

        // agent_id	int		是	给商户分配的唯一标识
        // agent_user_id	string	50	是	商户的个人会员ID。由数字、字母组成。
        // id_card	string	18	是	身份证号
        // personal_name	string	50	是	真实姓名。由2~5个汉字组成。
        // bank_type	string	10	是	银行卡类型
        // bank_no	string	20	是	银行卡
        // mobile	string	11	是	银行卡预留手机
        // email	string		否	邮箱.如果商户对接理财业务，必须传该参数.
        // return_url	string	255	是	绑定完成后同步返回商户的完整地址。
        // notify_url	string	255	是	绑定完成后异步通知商户的完整地址。
        // timestamp	long		是	时间戳。从1970-01-01 00:00:00算起的毫秒数
        // sign	string	32	是	验签参数。
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard", userBind.getIdCard());
        paramMap.put("personalName", userBind.getName());
        paramMap.put("bankType", userBind.getBankType());
        paramMap.put("bankNo", userBind.getBankNo());
        paramMap.put("mobile", userBind.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        // 构建表单
        final String form = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        log.info(form);
        return form;
    }

    /**
     * 通知
     *
     * @param paramMap 参数映射
     */
    // 添加事务
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void notify(Map<String, Object> paramMap) {
        // result_code	string		是	结果编码。
        //      0001=绑定成功且通过实名认证
        //      E105=绑定失败
        //      U999=未知错误
        // result_msg	string	100	是	绑定结果描述
        // bind_code	string	50	是	绑定账户协议号
        // agent_user_id	string	50	是	账户在商户的会员ID
        // timestamp	long		是	通知时间。从1970-01-01 00:00:00算起的毫秒数。绑定错误不返回。
        // sign	string	32	是	签名。

        // 更新账户绑定信息
        final UserBind userBind = baseMapper.selectOne(new LambdaQueryWrapper<UserBind>().eq(UserBind::getUserId, paramMap.get("agentUserId")));
        userBind.setBindCode((String) paramMap.get("bindCode"));
        userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
        baseMapper.updateById(userBind);

        // 更新用户信息
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(userBind.getUserId());
        userInfo.setName(userBind.getName());
        userInfo.setBindStatus(userBind.getStatus());
        userInfo.setBindCode(userBind.getBindCode());
        userInfoService.updateById(userInfo);
    }

    /**
     * 根据用户 ID 获取绑定协议号
     *
     * @param userId 用户 ID
     * @return {@link String}
     */
    @Override
    public String getBindCodeByUserId(Long userId) {
        return baseMapper
                .selectOne(new LambdaQueryWrapper<UserBind>().eq(UserBind::getUserId, userId))
                .getBindCode();
    }
}
