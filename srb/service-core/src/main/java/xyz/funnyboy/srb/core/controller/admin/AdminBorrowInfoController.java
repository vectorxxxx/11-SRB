package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.pojo.vo.BorrowInfoApprovalVO;
import xyz.funnyboy.srb.core.service.BorrowInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Api(tags = "借款管理")
@RestController
@RequestMapping("/admin/core/borrowInfo")
@Slf4j
public class AdminBorrowInfoController
{
    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation("借款信息列表")
    @GetMapping("/list")
    public R list() {
        final List<BorrowInfo> borrowInfoList = borrowInfoService.selectList();
        return R
                .ok()
                .data("list", borrowInfoList);
    }

    @ApiOperation("获取借款信息详情")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(name = "id",
                      value = "借款信息ID",
                      required = true)
            @PathVariable
                    Long id) {
        return R
                .ok()
                .data("borrowInfoDetail", borrowInfoService.getBorrowInfoDetail(id));
    }

    @ApiOperation("审批借款信息")
    @PostMapping("/approval")
    public R approval(
            @ApiParam(name = "borrowInfoApprovalVO",
                      value = "借款信息审批对象",
                      required = true)
            @RequestBody
                    BorrowInfoApprovalVO borrowInfoApprovalVO) {
        borrowInfoService.approval(borrowInfoApprovalVO);
        return R
                .ok()
                .message("审批完成");
    }
}

