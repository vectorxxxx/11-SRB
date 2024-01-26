package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.base.util.JwtUtils;
import xyz.funnyboy.srb.core.pojo.entity.LendItemReturn;
import xyz.funnyboy.srb.core.service.LendItemReturnService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 标的出借回款记录表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "回款计划")
@RestController
@RequestMapping("/api/core/lendItemReturn")
@Slf4j
public class LendItemReturnController
{
    @Resource
    private LendItemReturnService lendItemReturnService;

    @ApiOperation("获取列表")
    @RequestMapping("/list/{lendId}")
    public R list(
            @ApiParam(name = "lendId",
                      value = "标的id",
                      required = true)
            @PathVariable
                    Long lendId,

            HttpServletRequest request) {
        final Long userId = JwtUtils.getUserId(request.getHeader("token"));
        final List<LendItemReturn> lendItemReturnList = lendItemReturnService.selectByLendId(lendId, userId);
        return R
                .ok()
                .data("list", lendItemReturnList);
    }
}

