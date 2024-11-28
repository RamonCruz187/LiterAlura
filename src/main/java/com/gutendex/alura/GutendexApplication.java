package com.gutendex.alura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutendexApplication implements CommandLineRunner {
	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(GutendexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		principal.muestraElMenu();
	}
}
