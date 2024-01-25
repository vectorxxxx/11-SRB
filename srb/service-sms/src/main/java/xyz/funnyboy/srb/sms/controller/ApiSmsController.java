package xyz.funnyboy.srb.sms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.common.util.RandomUtils;
import xyz.funnyboy.common.util.RegexValidateUtils;
import xyz.funnyboy.srb.sms.client.CoreUserInfoClient;
import xyz.funnyboy.srb.sms.service.SmsService;
import xyz.funnyboy.srb.sms.util.SmsProperties;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 短信管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 13:22:26
 */
@Api(tags = "短信管理")
@RestController
@RequestMapping("/api/sms")
@CrossOrigin
@Slf4j
public class ApiSmsController
{
    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    @ApiOperation(value = "获取短信验证码")
    @GetMapping("send/{mobile}")
    public R sendSmsCode(
            @ApiParam(name = "mobile",
                      value = "手机号码",
                      required = true)
            @PathVariable
                    String mobile) {

        // 校验手机号码
        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);
        Assert.isTrue(!coreUserInfoClient.checkMobile(mobile), ResponseEnum.MOBILE_EXIST_ERROR);

        // 生成验证码
        final String code = RandomUtils.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, param);

        // 存入redis
        redisTemplate
                .opsForValue()
                .set("srb:sms:code:" + mobile, code, 5, TimeUnit.MINUTES);

        return R
                .ok()
                .message("验证码发送成功");
    }
}
