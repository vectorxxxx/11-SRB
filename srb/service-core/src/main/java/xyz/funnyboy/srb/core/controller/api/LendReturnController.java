package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.hfb.RequestHelper;
import xyz.funnyboy.srb.core.pojo.entity.LendReturn;
import xyz.funnyboy.srb.core.service.LendReturnService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "还款计划")
@RestController
@RequestMapping("/api/core/lendReturn")
@Slf4j
public class LendReturnController
{

    @Resource
    private LendReturnService lendReturnService;

    @ApiOperation("获取列表")
    @GetMapping("list/{lendId}")
    public R list(
            @ApiParam(name = "lendId",
                      value = "标的id",
                      required = true)
            @PathVariable
                    Long lendId) {
        List<LendReturn> list = lendReturnService.selectByLendId(lendId);
        return R
                .ok()
                .data("list", list);
    }

    @ApiOperation("用户还款")
    @PostMapping("/auth/commitReturn/{lendReturnId}")
    public R commitReturn(
            @ApiParam(name = "lendReturnId",
                      value = "还款记录id",
                      required = true)
            @PathVariable
                    Long lendReturnId,

            HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        final String formStr = lendReturnService.commitReturn(lendReturnId, userId);
        return R
                .ok()
                .data("formStr", formStr);
    }

    @ApiOperation("用户还款异步回调")
    @PostMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request) {
        // 获取参数
        final Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());

        // 验签
        if (!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户还款异步回调验签失败");
            // return "fail";
        }

        // result_code	string		是	结果编码：
        //      0001=还款扣款成功
        //      E105=扣款失败。该错误记录了批次号
        //      E106=数据验证
        //      U999=未知错误
        //      result_msg	string	100	是	扣款结果描述
        if (!"0001".equals(paramMap.get("resultCode"))) {
            log.error("用户还款异步回调扣款失败");
            return "fail";
        }

        lendReturnService.notify(paramMap);
        log.info("用户还款异步回调扣款成功");
        return "success";
    }
}

