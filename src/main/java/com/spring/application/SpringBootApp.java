package com.spring.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.n11.conference.entity")
@EnableJpaRepositories("com.n11.conference.repository")
@ComponentScan(basePackages = { "com.n11.conference.controller", "com.n11.conference.service*",
		"com.spring.configuration" })
public class SpringBootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}

}
