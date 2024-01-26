package xyz.funnyboy.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.service.UserAccountService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户账户 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "会员账户")
@RestController
@RequestMapping("/api/core/userAccount")
@Slf4j
public class UserAccountController
{
    @Resource
    private UserAccountService userAccountService;

    @ApiOperation("充值")
    @PostMapping("/auth/commitCharge/{chargeAmt}")
    public R commitCharge(
            @ApiParam(name = "chargeAmt",
                      value = "充值金额",
                      required = true)
            @PathVariable
                    Long chargeAmt,

            HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        final String formStr = userAccountService.commitCharge(chargeAmt, userId);
        return R
                .ok()
                .data("formStr", formStr);
    }

    @ApiOperation("用户充值异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        final Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("用户充值异步回调：{}", JSON.toJSONString(paramMap));

        if (!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户充值异步回调签名验证失败");
            // return "fail";
        }

        // result_code	string		是	结果编码：
        //                          0001=充值成功
        if (!"0001".equals(paramMap.get("resultCode"))) {
            log.error("用户充值异步回调充值失败：{}", JSON.toJSONString(paramMap));
            return "fail";
        }

        log.info("用户充值异步回调充值成功：" + JSON.toJSONString(paramMap));
        userAccountService.notify(paramMap);
        return "success";
    }
}

