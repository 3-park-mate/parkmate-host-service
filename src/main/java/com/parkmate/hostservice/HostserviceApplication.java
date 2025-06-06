package com.parkmate.hostservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HostserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostserviceApplication.class, args);
	}

}
