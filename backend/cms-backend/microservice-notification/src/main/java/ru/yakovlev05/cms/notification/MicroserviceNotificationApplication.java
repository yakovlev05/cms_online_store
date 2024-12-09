package ru.yakovlev05.cms.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.yakovlev05.cms")
public class MicroserviceNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceNotificationApplication.class, args);
    }

}
