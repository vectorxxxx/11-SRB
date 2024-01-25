package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerVO;
import xyz.funnyboy.srb.core.service.BorrowerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "借款人")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class BorrowerController
{
    @Resource
    private BorrowerService borrowerService;

    @ApiOperation("保存借款人信息")
    @PostMapping("/auth/save")
    public R saveBorrower(
            @ApiParam(name = "borrowerVO",
                      value = "借款人信息",
                      required = true)
            @RequestBody
                    BorrowerVO borrowerVO,

            @ApiParam(name = "request",
                      value = "请求信息",
                      required = true)
                    HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        borrowerService.saveBorrower(borrowerVO, userId);
        return R
                .ok()
                .message("信息提交成功");
    }

    @ApiOperation("获取借款人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(
            @ApiParam(name = "request",
                      value = "请求信息",
                      required = true)
                    HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        Integer status = borrowerService.getBorrowerStatus(userId);
        return R
                .ok()
                .data("borrowerStatus", status);
    }
}

