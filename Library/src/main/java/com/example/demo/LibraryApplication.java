package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(BookRepository repository) {
		return args -> {
			repository.save(new Book("The Iron Mountains", "H.F. Larkin", "Blackwood Press"));
			repository.save(new Book("Echoes of the Last Frontier", "Marina Kove", "NorthStar Publishing"));
			
			repository.findByTitle("The Iron Mountains").forEach( e ->
					System.out.println(e)
					);
			
			repository.findAll().forEach(e -> 
					System.out.println(e)
					);
		};
	}
	
}
