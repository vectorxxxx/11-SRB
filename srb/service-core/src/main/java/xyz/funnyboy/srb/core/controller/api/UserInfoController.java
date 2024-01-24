package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.common.util.RegexValidateUtils;
import xyz.funnyboy.srb.core.pojo.vo.RegisterVO;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@CrossOrigin
@Slf4j
public class UserInfoController
{
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(
            @ApiParam(name = "registerVO",
                      value = "注册对象",
                      required = true)
            @RequestBody
                    RegisterVO registerVO) {
        final String mobile = registerVO.getMobile();
        final String code = registerVO.getCode();
        final String password = registerVO.getPassword();

        // 校验手机号
        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 校验密码
        Assert.notNull(password, ResponseEnum.PASSWORD_NULL_ERROR);

        // 校验验证码
        Assert.notNull(code, ResponseEnum.CODE_NULL_ERROR);
        final String codeGen = (String) redisTemplate
                .opsForValue()
                .get("srb:sms:code:" + mobile);
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        // 注册
        userInfoService.register(registerVO);
        return R
                .ok()
                .message("注册成功");
    }
}

