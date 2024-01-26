package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.service.BorrowInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "借款信息")
@RestController
@RequestMapping("/api/core/borrowInfo")
@Slf4j
public class BorrowInfoController
{
    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation("获取借款额度")
    @GetMapping("/auth/getBorrowAmount")
    public R getBorrowAmount(HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        return R
                .ok()
                .data("borrowAmount", borrowInfoService.getBorrowAmount(userId));
    }

    @ApiOperation("提交借款申请")
    @PostMapping("/auth/save")
    public R save(
            @ApiParam(name = "borrowInfoVO",
                      value = "借款信息",
                      required = true)
            @RequestBody
                    BorrowInfo borrowInfo, HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        borrowInfoService.saveBorrowInfo(borrowInfo, userId);
        return R
                .ok()
                .message("提交借款申请成功");
    }

    @ApiOperation("获取借款申请审批状态")
    @GetMapping("/auth/getBorrowInfoStatus")
    public R getBorrowInfoStatus(HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        Integer borrowInfoStatus = borrowInfoService.getBorrowInfoStatus(userId);
        return R
                .ok()
                .data("borrowInfoStatus", borrowInfoStatus);
    }
}

