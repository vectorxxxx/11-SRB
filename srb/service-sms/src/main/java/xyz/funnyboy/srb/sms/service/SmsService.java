package xyz.funnyboy.srb.sms.service;

import java.util.Map;

/**
 * 短信服务
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 13:06:35
 */
public interface SmsService
{
    /**
     * 发送短信
     *
     * @param mobile       手机号码
     * @param templateCode 验证码
     * @param param        参数
     */
    void send(String mobile, String templateCode, Map<String, Object> param);
}
