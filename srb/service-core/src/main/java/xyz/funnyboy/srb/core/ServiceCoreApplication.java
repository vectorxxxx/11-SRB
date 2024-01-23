package xyz.funnyboy.srb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-22 23:36:11
 */
@SpringBootApplication
@ComponentScan(basePackages = {"xyz.funnyboy.srb", "xyz.funnyboy.common"})
public class ServiceCoreApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);
    }
}
