package com.clyday.clyday_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// A criação do tutor inicial de exemplo fica centralizada em
// config.DataInitializer, evitando dois CommandLineRunners duplicados
// fazendo a mesma coisa.
@SpringBootApplication
public class ClydayApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				ClydayApiApplication.class,
				args
		);
	}
}