package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class D3Ud3CarritoCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(D3Ud3CarritoCompraApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(ProductRepository repository) {
		return args -> {
			repository.save(new Product("Platanos", 1.30, 10));
			repository.save(new Product("Kiwis", 0.90, 20));
			repository.save(new Product("Naranjas", 3.00, 15));
		};
	}
}
