package xyz.funnyboy.srb.sms.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.sms.client.CoreUserInfoClient;

/**
 * 服务容错类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-25 09:35:10
 */
@Service
@Slf4j
public class CoreUserInfoClientFallback implements CoreUserInfoClient
{
    @Override
    public boolean checkMobile(String mobile) {
        log.error("checkMobile远程调用失败，服务熔断");
        // 当无法校验手机号是否已注册时，直接发送短信
        return false;
    }
}
