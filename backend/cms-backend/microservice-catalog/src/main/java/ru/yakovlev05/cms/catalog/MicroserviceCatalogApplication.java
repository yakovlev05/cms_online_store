package ru.yakovlev05.cms.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.yakovlev05.cms")
public class MicroserviceCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCatalogApplication.class, args);
    }

}
