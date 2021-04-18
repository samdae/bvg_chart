package com.braveg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BvgApplication {
	public static void main(String[] args) {
		SpringApplication.run(BvgApplication.class, args);
	}

}
