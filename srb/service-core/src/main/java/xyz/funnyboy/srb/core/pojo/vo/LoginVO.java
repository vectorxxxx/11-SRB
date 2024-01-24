package xyz.funnyboy.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录 VO
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/24
 */
@Data
@ApiModel(description = "登录对象")
public class LoginVO
{

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
