package xyz.funnyboy.srb.rabbitutil.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-27 14:44:45
 */
@Configuration
public class MQConfig
{
    @Bean
    public MessageConverter messageConverter() {
        // json字符串转换器
        return new Jackson2JsonMessageConverter();
    }
}
