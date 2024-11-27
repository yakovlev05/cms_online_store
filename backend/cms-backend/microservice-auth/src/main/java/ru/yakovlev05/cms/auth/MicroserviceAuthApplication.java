package ru.yakovlev05.cms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.yakovlev05.cms")
public class MicroserviceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAuthApplication.class, args);
    }

}
