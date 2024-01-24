package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.UserLoginRecord;
import xyz.funnyboy.srb.core.service.UserLoginRecordService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员登录日志接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 23:50:17
 */
@Api(tags = "会员登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@CrossOrigin
@Slf4j
public class AdminUserLoginRecordController
{
    @Resource
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation("获取会员登录日志列表")
    @GetMapping("listTop50/{userId}")
    public R listTop50(
            @ApiParam(name = "userId",
                      value = "会员id",
                      required = true)
            @PathVariable
                    Long userId) {
        List<UserLoginRecord> userLoginRecordList = userLoginRecordService.listTop50(userId);
        return R
                .ok()
                .data("list", userLoginRecordList);
    }
}
