package xyz.funnyboy.srb.rabbitutil.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-27 14:47:43
 */
@Service
@Slf4j
public class MQService
{
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     *
     * @param exchange   交换
     * @param routingKey 路由
     * @param message    消息
     * @return boolean
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        log.info("发送消息：exchange={}, routingKey={}, message={}", exchange, routingKey, message);
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}
