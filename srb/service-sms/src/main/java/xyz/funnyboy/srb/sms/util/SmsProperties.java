package xyz.funnyboy.srb.sms.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 常量读取工具类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 23:25:52
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean
{
    @Value("${aliyun.sms.region-id}")
    private String regionId;

    @Value("${aliyun.sms.key-id}")
    private String keyId;

    @Value("${aliyun.sms.key-secret}")
    private String keySecret;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    public static String REGION_Id;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String TEMPLATE_CODE;
    public static String SIGN_NAME;

    /**
     * 当私有成员被赋值后，此方法自动被调用，从而初始化常量
     *
     * @throws Exception 例外
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_Id = regionId;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        TEMPLATE_CODE = templateCode;
        SIGN_NAME = signName;
    }
}
