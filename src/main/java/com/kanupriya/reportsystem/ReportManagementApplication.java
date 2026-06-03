package com.kanupriya.reportsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReportManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportManagementApplication.class, args);
	}

}
