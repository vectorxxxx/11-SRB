package xyz.funnyboy.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.funnyboy.common.result.ResponseEnum;

/**
 * 自定义异常类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 13:14:48
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException
{
    //错误码
    private Integer code;

    //错误消息
    private String message;

    /**
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this.message = message;
    }

    /**
     * @param message 错误消息
     * @param code    错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     * @param message 错误消息
     * @param code    错误码
     * @param cause   原始异常对象
     */
    public BusinessException(String message, Integer code, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    /**
     * @param resultCodeEnum 接收枚举类型
     */
    public BusinessException(ResponseEnum resultCodeEnum) {
        this.message = resultCodeEnum.getMessage();
        this.code = resultCodeEnum.getCode();
    }

    /**
     * @param resultCodeEnum 接收枚举类型
     * @param cause          原始异常对象
     */
    public BusinessException(ResponseEnum resultCodeEnum, Throwable cause) {
        super(cause);
        this.message = resultCodeEnum.getMessage();
        this.code = resultCodeEnum.getCode();
    }
}
