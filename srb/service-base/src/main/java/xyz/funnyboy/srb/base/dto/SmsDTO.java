package xyz.funnyboy.srb.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 短信 DTO
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/27
 */
@Data
@ApiModel(description = "短信")
public class SmsDTO
{

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "消息内容")
    private String message;
}
