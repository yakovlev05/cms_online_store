package ru.yakovlev05.cms.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.yakovlev05.cms")
public class MicroserviceCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCartApplication.class, args);
	}

}
