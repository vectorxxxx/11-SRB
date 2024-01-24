package xyz.funnyboy.srb.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.funnyboy.srb.sms.util.SmsProperties;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 23:31:47
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UtilTest
{
    @Test
    public void testProperties() {
        System.out.println(SmsProperties.REGION_Id);
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.KEY_SECRET);
        System.out.println(SmsProperties.TEMPLATE_CODE);
        System.out.println(SmsProperties.SIGN_NAME);
    }
}
