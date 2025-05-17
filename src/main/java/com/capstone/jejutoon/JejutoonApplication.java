package com.capstone.jejutoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JejutoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(JejutoonApplication.class, args);
	}

}
