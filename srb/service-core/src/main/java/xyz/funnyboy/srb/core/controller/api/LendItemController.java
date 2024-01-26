package xyz.funnyboy.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.pojo.vo.InvestVO;
import xyz.funnyboy.srb.core.service.LendItemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "标的投资")
@RestController
@RequestMapping("/api/core/lendItem")
@Slf4j
public class LendItemController
{
    @Resource
    private LendItemService lendItemService;

    @ApiOperation("会员投资提交数据")
    @PostMapping("/auth/commitInvest")
    public R commitInvest(
            @ApiParam(name = "investVO",
                      value = "标的投资对象",
                      required = true)
            @RequestBody
                    InvestVO investVO,

            @ApiParam(name = "request",
                      value = "请求对象",
                      required = true)
                    HttpServletRequest request) {
        final String token = request.getHeader("token");
        final Long userId = JwtUtils.getUserId(token);
        final String userName = JwtUtils.getUserName(token);
        log.info("用户id: {}, 用户名: {}", userId, userName);

        investVO.setInvestUserId(userId);
        investVO.setInvestName(userName);
        final String formStr = lendItemService.commitInvest(investVO);
        // 构建充值自动提交表单
        return R
                .ok()
                .data("formStr", formStr);
    }

    @ApiOperation("会员投资异步回调")
    @PostMapping("/notify")
    public String notify(
            @ApiParam(name = "request",
                      value = "请求对象",
                      required = true)
                    HttpServletRequest request) {
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("用户投资异步回调：" + JSON.toJSONString(paramMap));

        if (!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户投资异步回调签名错误：{}", JSON.toJSONString(paramMap));
            // return "fail";
        }

        if (!"0001".equals(paramMap
                .get("resultCode")
                .toString())) {
            log.info("用户投资异步回调失败：{}", JSON.toJSONString(paramMap));
            return "fail";
        }

        log.info("用户投资异步回调成功：{}", JSON.toJSONString(paramMap));
        lendItemService.notify(paramMap);
        return "success";
    }

    @ApiOperation("获取列表")
    @GetMapping("/list/{lendId}")
    public R list(
            @ApiParam(name = "lendId",
                      value = "标的id",
                      required = true)
            @PathVariable
                    Long lendId) {
        List<LendItem> lendItemList = lendItemService.selectByLendId(lendId);
        return R
                .ok()
                .data("list", lendItemList);
    }
}

