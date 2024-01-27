package xyz.funnyboy.srb.sms.receiver;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.funnyboy.common.util.RandomUtils;
import xyz.funnyboy.srb.base.dto.SmsDTO;
import xyz.funnyboy.srb.rabbitutil.constant.MQConst;
import xyz.funnyboy.srb.sms.service.SmsService;
import xyz.funnyboy.srb.sms.util.SmsProperties;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-27 14:55:53
 */
@Component
@Slf4j
public class SmsReceiver
{
    @Resource
    private SmsService smsService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = MQConst.QUEUE_SMS_ITEM,
                                                            durable = "true"),
                                             exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
                                             key = {MQConst.ROUTING_SMS_ITEM}))
    public void send(SmsDTO smsDTO) throws IOException {
        log.info("消息监听：{}", JSON.toJSONString(smsDTO));
        Map<String, Object> param = new HashMap<>();
        // 这里模板不支持参数，所以直接写死
        // param.put("code", smsDTO.getMessage());
        param.put("code", RandomUtils.getFourBitRandom());
        smsService.send(smsDTO.getMobile(), SmsProperties.TEMPLATE_CODE, param);
    }
}
