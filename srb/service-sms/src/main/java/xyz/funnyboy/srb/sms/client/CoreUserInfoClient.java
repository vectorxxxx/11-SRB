package xyz.funnyboy.srb.sms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.srb.sms.client.fallback.CoreUserInfoClientFallback;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-25 08:54:42
 */
@FeignClient(value = "service-core",
             fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient
{
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    boolean checkMobile(
            @PathVariable("mobile")
                    String mobile);
}
