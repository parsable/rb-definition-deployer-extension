package com.parsable.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.parsable.application", "com.parsable.rootstarter"})
public class ApplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationApplication.class, args);
	}
}
