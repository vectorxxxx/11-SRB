package xyz.funnyboy.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 23:24:43
 */
@SpringBootApplication
@ComponentScan(basePackages = {"xyz.funnyboy.srb", "xyz.funnyboy.common"})
public class ServiceSmsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
