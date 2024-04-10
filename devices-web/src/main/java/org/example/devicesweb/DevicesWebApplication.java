package org.example.devicesweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class DevicesWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevicesWebApplication.class, args);
    }

}
