package xyz.funnyboy.srb.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ServiceOssApplication
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 15:16:01
 */
@SpringBootApplication
@ComponentScan({"xyz.funnyboy.srb", "xyz.funnyboy.common"})
public class ServiceOssApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}
