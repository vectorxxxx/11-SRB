package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.service.LendService;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
@Api(tags = "标的")
@RestController
@RequestMapping("/api/core/lend")
@Slf4j
public class LendController
{
    @Resource
    private LendService lendService;

    @ApiOperation("标的列表")
    @GetMapping("/list")
    public R list() {
        List<Lend> lendList = lendService.selectList();
        return R
                .ok()
                .data("lendList", lendList);
    }

    @ApiOperation("标的详情")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(name = "id",
                      value = "标的id",
                      required = true)
            @PathVariable
                    Long id) {
        final Map<String, Object> lendDetail = lendService.getLendDetail(id);
        return R
                .ok()
                .data("lendDetail", lendDetail);
    }

    @ApiOperation("计算投资收益")
    @GetMapping("/getInterestCount/{invest}/{yearRate}/{totalmonth}/{returnMethod}")
    public R getInterestCount(
            @ApiParam(name = "invest",
                      value = "投资金额",
                      required = true)
            @PathVariable
                    BigDecimal invest,

            @ApiParam(name = "yearRate",
                      value = "年化收益",
                      required = true)
            @PathVariable
                    BigDecimal yearRate,

            @ApiParam(name = "totalmonth",
                      value = "期数",
                      required = true)
            @PathVariable
                    Integer totalmonth,

            @ApiParam(name = "returnMethod",
                      value = "还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本还息",
                      required = true)
            @PathVariable
                    Integer returnMethod) {
        BigDecimal interestCount = lendService.getInterestCount(invest, yearRate, totalmonth, returnMethod);
        return R
                .ok()
                .data("interestCount", interestCount);
    }
}

