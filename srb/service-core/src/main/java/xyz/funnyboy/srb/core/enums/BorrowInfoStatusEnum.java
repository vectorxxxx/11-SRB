package xyz.funnyboy.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 借款信息状态枚举
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/26
 * @see Enum
 */
@AllArgsConstructor
@Getter
public enum BorrowInfoStatusEnum
{

    NO_AUTH(0, "未认证"),
    CHECK_RUN(1, "审核中"),
    CHECK_OK(2, "审核通过"),
    CHECK_FAIL(-1, "审核不通过"),
    ;

    private final Integer status;
    private final String msg;

    public static String getMsgByStatus(int status) {
        BorrowInfoStatusEnum[] arrObj = BorrowInfoStatusEnum.values();
        for (BorrowInfoStatusEnum obj : arrObj) {
            if (status == obj.getStatus()) {
                return obj.getMsg();
            }
        }
        return "";
    }
}
