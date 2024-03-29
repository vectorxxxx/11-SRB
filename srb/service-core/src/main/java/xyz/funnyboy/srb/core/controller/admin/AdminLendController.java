package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.service.LendService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "标的管理")
@RestController
@RequestMapping("/admin/core/lend")
@Slf4j
public class AdminLendController
{
    @Resource
    private LendService lendService;

    @ApiOperation("标的列表")
    @GetMapping("/list")
    public R list() {
        List<Lend> lendList = lendService.selectList();
        return R
                .ok()
                .data("list", lendList);
    }

    @ApiOperation("获取标的信息")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(value = "标的id",
                      required = true)
            @PathVariable
                    Long id) {
        Map<String, Object> result = lendService.getLendDetail(id);
        return R
                .ok()
                .data("lendDetail", result);
    }

    @ApiOperation("放款")
    @PostMapping("/makeLoan/{id}")
    public R makeLoan(
            @ApiParam(value = "标的id",
                      required = true)
            @PathVariable
                    Long id) {
        lendService.makeLoan(id);
        return R
                .ok()
                .message("放款成功");
    }
}

