package xyz.funnyboy.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 投标信息VO
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/26
 */
@Data
@ApiModel(description = "投标信息")
public class InvestVO
{

    private Long lendId;

    // 投标金额
    private String investAmount;

    // 用户id
    private Long investUserId;

    // 用户姓名
    private String investName;
}
