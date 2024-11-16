package com.finEasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.finEasy.*"})

public class ModelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelsApplication.class, args);
	}

}
