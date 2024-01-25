package xyz.funnyboy.srb.core.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerApprovalVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerDetailVO;
import xyz.funnyboy.srb.core.service.BorrowerService;

import javax.annotation.Resource;

/**
 * 借款人管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-25 23:21:16
 */
@Api(tags = "借款人管理")
@RestController
@RequestMapping("/admin/core/borrower")
@Slf4j
public class AdminBorrowerController
{
    @Resource
    private BorrowerService borrowerService;

    @ApiOperation("获取借款人分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(
            @ApiParam(name = "page",
                      value = "页码",
                      required = true)
            @PathVariable("page")
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页数量",
                      required = true)
            @PathVariable("limit")
                    Long limit,

            @ApiParam(name = "keyword",
                      value = "查询条件",
                      required = false)
            @RequestParam(name = "keyword",
                          required = false)
                    String keyword) {
        final Page<Borrower> pageParam = new Page<>(page, limit);
        Page<Borrower> pageModel = borrowerService.listPage(pageParam, keyword);
        return R
                .ok()
                .data("pageModel", pageModel);
    }

    @ApiOperation("获取借款人信息")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(value = "借款人id",
                      required = true)
            @PathVariable
                    Long id) {
        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(id);
        return R
                .ok()
                .data("borrowerDetailVO", borrowerDetailVO);
    }

    @ApiOperation("借款额度审批")
    @PostMapping("/approval")
    public R approval(
            @ApiParam(name = "borrowerApprovalVO",
                      value = "借款额度审批对象",
                      required = true)
            @RequestBody
                    BorrowerApprovalVO borrowerApprovalVO) {
        borrowerService.approval(borrowerApprovalVO);
        return R
                .ok()
                .message("审批完成");
    }
}
