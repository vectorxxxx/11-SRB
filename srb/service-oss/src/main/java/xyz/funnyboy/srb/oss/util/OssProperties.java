package xyz.funnyboy.srb.oss.util;

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
 * @date 2024-01-24 15:17:34
 */

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties implements InitializingBean
{
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.key-id}")
    private String keyId;

    @Value("${aliyun.oss.key-secret}")
    private String keySecret;

    @Value("${aliyun.oss.endpoint}")
    private String bucketName;

    public static String ENDPOINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
