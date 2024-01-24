package xyz.funnyboy.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员搜索对象
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 23:11:21
 */
@Data
@ApiModel(description = "会员搜索对象")
public class UserInfoQuery
{
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "1：出借人 2：借款人")
    private Integer userType;
}
