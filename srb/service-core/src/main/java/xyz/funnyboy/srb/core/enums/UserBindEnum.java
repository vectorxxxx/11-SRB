package xyz.funnyboy.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户绑定枚举
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/25
 * @see Enum
 */
@AllArgsConstructor
@Getter
public enum UserBindEnum
{

    NO_BIND(0, "未绑定"),
    BIND_OK(1, "绑定成功"),
    BIND_FAIL(-1, "绑定失败"),
    ;

    private final Integer status;
    private final String msg;
}
