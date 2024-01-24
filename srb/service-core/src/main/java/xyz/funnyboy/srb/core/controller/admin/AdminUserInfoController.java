package xyz.funnyboy.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.UserInfo;
import xyz.funnyboy.srb.core.pojo.query.UserInfoQuery;
import xyz.funnyboy.srb.core.service.UserInfoService;

import javax.annotation.Resource;

/**
 * 会员管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 23:18:38
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@CrossOrigin
@Slf4j
public class AdminUserInfoController
{
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R list(
            @ApiParam(name = "page",
                      value = "页码",
                      required = true)
            @PathVariable
                    Integer page,

            @ApiParam(name = "limit",
                      value = "每页数量",
                      required = true)
            @PathVariable
                    Integer limit,

            @ApiParam(name = "searchObj",
                      value = "查询对象",
                      required = false)
            @RequestParam(name = "searchObj",
                          required = false)
                    UserInfoQuery userInfoQuery) {
        final Page<UserInfo> pageParam = new Page<>(page, limit);
        final IPage<UserInfo> pageModel = userInfoService.listPage(pageParam, userInfoQuery);
        return R
                .ok()
                .data("pageModel", pageModel);
    }

    @ApiOperation("锁定和解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(name = "id",
                      value = "会员id",
                      required = true)
            @PathVariable
                    Long id,

            @ApiParam(name = "status",
                      value = "状态",
                      required = true)
            @PathVariable
                    Integer status) {
        userInfoService.lock(id, status);
        return R
                .ok()
                .message(status == 1 ?
                         "解锁成功" :
                         "锁定成功");
    }
}
