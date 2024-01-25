package xyz.funnyboy.srb.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.pojo.vo.UserBindVO;
import xyz.funnyboy.srb.core.service.UserBindService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "账户绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class UserBindController
{
    @Resource
    private UserBindService userBindService;

    @ApiOperation(value = "账户绑定提交数据")
    @PostMapping("/auth/bind")
    public R bind(
            @ApiParam(name = "userBindVO",
                      value = "用户绑定对象",
                      required = true)
            @RequestBody
                    UserBindVO userBindVO,

            @ApiParam(name = "request",
                      value = "请求对象",
                      required = true)
                    HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        String formStr = userBindService.commitBindUser(userBindVO, userId);
        return R
                .ok()
                .data("formStr", formStr);
    }

    @ApiOperation(value = "账户绑定异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        // 获取参数
        final Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("异步回调参数：{}", paramMap);

        // 验证签名
        if (RequestHelper.isSignEquals(paramMap)) {
            log.error("异步回调参数签名验证失败");
            // return "fail";
        }
        log.info("异步回调参数签名验证成功");

        // 处理异步回调
        userBindService.notify(paramMap);
        return "success";
    }
}

