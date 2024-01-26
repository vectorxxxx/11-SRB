package xyz.funnyboy.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 还款方式枚举
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/26
 * @see Enum
 */
@AllArgsConstructor
@Getter
public enum ReturnMethodEnum
{

    ONE(1, "等额本息"),
    TWO(2, "等额本金"),
    THREE(3, "每月还息一次还本"),
    FOUR(4, "一次还本还息"),
    ;

    private final Integer method;
    private final String msg;
}
