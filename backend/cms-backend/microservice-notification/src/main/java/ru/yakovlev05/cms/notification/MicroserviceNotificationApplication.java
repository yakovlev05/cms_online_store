package ru.yakovlev05.cms.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "ru.yakovlev05.cms", exclude = {UserDetailsServiceAutoConfiguration.class})
public class MicroserviceNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceNotificationApplication.class, args);
    }

}
