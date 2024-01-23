package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.exception.Assert;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.pojo.entity.IntegralGrade;
import xyz.funnyboy.srb.core.service.IntegralGradeService;

import java.util.List;

/**
 * 积分等级管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 12:40:24
 */
@Api(tags = "积分等级管理")
@RestController
@RequestMapping("/admin/core/integralGrade")
@CrossOrigin
@Slf4j
public class AdminIntegralGradeController
{
    @Autowired
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "获取积分等级列表")
    @GetMapping("/list")
    public R listAll() {
        log.info("hi i'm helen");
        log.warn("warning!!!");
        log.error("it's a error");

        final List<IntegralGrade> list = integralGradeService.list();
        return R
                .ok()
                .data("list", list);
    }

    @ApiOperation(value = "根据id删除积分等级",
                  notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(name = "id",
                      value = "积分等级ID",
                      required = true)
            @PathVariable
                    Long id) {
        final boolean remove = integralGradeService.removeById(id);
        return remove ?
               R
                       .ok()
                       .message("删除成功") :
               R
                       .error()
                       .message("删除失败");
    }

    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "integralGrade",
                      value = "积分等级对象",
                      required = true)
            @RequestBody
                    IntegralGrade integralGrade) {
        // 如果借款额度为空就手动抛出一个自定义的异常！
        // if (integralGrade.getBorrowAmount() == null) {
        //     throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        // }
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);

        boolean save = integralGradeService.save(integralGrade);
        return save ?
               R
                       .ok()
                       .message("保存成功") :
               R
                       .error()
                       .message("保存失败");
    }

    @ApiOperation("根据id获取积分等级")
    @GetMapping("/get/{id}")
    public R getById(
            @ApiParam(name = "id",
                      value = "数据id",
                      required = true,
                      example = "1")
            @PathVariable
                    Long id) {
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null) {
            return R
                    .ok()
                    .data("record", integralGrade);
        }
        else {
            return R
                    .error()
                    .message("数据不存在");
        }
    }

    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public R updateById(
            @ApiParam(name = "integralGrade",
                      value = "积分等级对象",
                      required = true)
            @RequestBody
                    IntegralGrade integralGrade) {
        boolean update = integralGradeService.updateById(integralGrade);
        return update ?
               R
                       .ok()
                       .message("修改成功") :
               R
                       .error()
                       .message("修改失败");
    }
}
