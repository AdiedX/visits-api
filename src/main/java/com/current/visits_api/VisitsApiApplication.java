package com.current.visits_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VisitsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(VisitsApiApplication.class, args);
	}
}
