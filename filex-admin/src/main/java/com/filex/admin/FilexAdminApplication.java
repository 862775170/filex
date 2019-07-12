package com.filex.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.filex")
public class FilexAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilexAdminApplication.class, args);
	}

}
