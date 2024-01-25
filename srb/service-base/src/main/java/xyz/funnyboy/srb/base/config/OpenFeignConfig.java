package xyz.funnyboy.srb.base.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>OpenFeign配置</h1>
 * <p>
 * OpenFeign提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解OpenFeign中Http请求的细节。
 * <p>
 * 即对OpenFeign远程接口调用的情况进行监控和日志输出。
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-25 09:01:47
 */
@Configuration
public class OpenFeignConfig
{
    @Bean
    public Logger.Level feignLoggerLevel() {
        // NONE：默认级别，不显示日志
        // BASIC：仅记录请求方法、URL、响应状态及执行时间
        // HEADERS：除了BASIC中定义的信息之外，还有请求和响应头信息
        // FULL：除了HEADERS中定义的信息之外，还有请求和响应正文及元数据信息
        return Logger.Level.FULL;
    }
}
