package xyz.funnyboy.srb.oss.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.common.exception.BusinessException;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;
import xyz.funnyboy.srb.oss.service.FileService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 阿里云文件管理
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 15:26:09
 */
@Api(tags = "阿里云文件管理")
@RestController
@RequestMapping("/api/oss/file")
@CrossOrigin
@Slf4j
public class FileController
{
    @Resource
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload")
    public R uploadFile(
            @ApiParam(name = "file",
                      value = "文件",
                      required = true)
            @RequestParam(value = "file")
                    MultipartFile file,

            @ApiParam(name = "module",
                      value = "模块",
                      required = true)
            @RequestParam(value = "module",
                          required = true)
                    String module) {
        try {
            final String url = fileService.uploadFile(file.getInputStream(), "oss", file.getOriginalFilename());
            return R
                    .ok()
                    .message("上传文件成功")
                    .data("url", url);
        }
        catch (IOException e) {
            log.error("上传文件失败: " + e.getMessage(), e);
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/remove")
    public R removeFile(
            @ApiParam(name = "url",
                      value = "文件地址",
                      required = true)
            @RequestParam(value = "url",
                          required = true)
                    String url) {
        fileService.removeFile(url);
        return R
                .ok()
                .message("删除文件成功");
    }
}
