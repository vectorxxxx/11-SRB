package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.service.LendItemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标的出借记录表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "标的投资管理")
@RestController
@RequestMapping("/admin/core/lendItem")
@Slf4j
public class AdminLendItemController
{
    @Resource
    private LendItemService lendItemService;

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

