package xyz.funnyboy.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.common.result.ResponseEnum;

/**
 * 统一异常处理器
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 12:58:54
 */
@Slf4j
@Component
// 在controller层添加通知。如果使用@ControllerAdvice，则方法上需要添加@ResponseBody
@RestControllerAdvice
public class UnifiedExceptionHandler
{
    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return R.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return R
                .error()
                .message(e.getMessage())
                .code(e.getCode());
    }

    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
                              NoHandlerFoundException.class,
                              HttpRequestMethodNotSupportedException.class,
                              HttpMediaTypeNotSupportedException.class,
                              MissingPathVariableException.class,
                              MissingServletRequestParameterException.class,
                              TypeMismatchException.class,
                              HttpMessageNotReadableException.class,
                              HttpMessageNotWritableException.class,
                              MethodArgumentNotValidException.class,
                              HttpMediaTypeNotAcceptableException.class,
                              ServletRequestBindingException.class,
                              ConversionNotSupportedException.class,
                              MissingServletRequestPartException.class,
                              AsyncRequestTimeoutException.class
                      })
    public R handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        // SERVLET_ERROR(-102, "servlet请求异常"),
        return R
                .error()
                .message(ResponseEnum.SERVLET_ERROR.getMessage())
                .code(ResponseEnum.SERVLET_ERROR.getCode());
    }
}
