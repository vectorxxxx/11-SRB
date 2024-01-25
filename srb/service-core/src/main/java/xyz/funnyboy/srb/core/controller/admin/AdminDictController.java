package xyz.funnyboy.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.common.exception.BusinessException;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.core.service.DictService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * 数据字典管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 21:22:43
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
// @CrossOrigin
@Slf4j
public class AdminDictController
{
    @Autowired
    private DictService dictService;

    @ApiOperation(value = "导入数据")
    @PostMapping("/import")
    public R importData(
            @ApiParam(name = "file",
                      value = "Excel文件",
                      required = true)
            @RequestParam
                    MultipartFile file) {
        try {
            dictService.importData(file.getInputStream());
            return R.ok();
        }
        catch (IOException e) {
            log.error("Excel文件导入失败: " + e.getMessage(), e);
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation(value = "导出数据")
    @GetMapping("/export")
    public R exportData(HttpServletResponse response) {
        try {
            // 设置内容类型
            response.setContentType("application/vnd.ms-excel");
            // 设置编码
            response.setCharacterEncoding(StandardCharsets.UTF_8
                    .name()
                    .toUpperCase(Locale.ROOT));
            // 设置文件名
            String fileName = URLEncoder.encode("mydict", StandardCharsets.UTF_8.name())
                                        // 在URL中，"+"是特殊字符之一。它通常用于表示空格。当浏览器发送表单数据时，空格会被编码为"+"。
                                        // 因此，将"+"替换为"%20"可以确保在URL中正确处理空格。
                                        .replaceAll("\\+", "%20");
            // 设置响应头
            response.setHeader("Content-disposition", String.format("attachment;filename=%s.xlsx", fileName));
            // 导出数据
            dictService.exportData(response.getOutputStream());
            return R.ok();
        }
        catch (Exception e) {
            log.error("数据导出失败: " + e.getMessage(), e);
            throw new BusinessException(ResponseEnum.EXPORT_DATA_ERROR, e);
        }
    }

    @ApiOperation(value = "根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(
            @ApiParam(name = "parentId",
                      value = "上级id",
                      required = true)
            @PathVariable
                    Long parentId) {
        return R
                .ok()
                .data("list", dictService.listByParentId(parentId));
    }
}
