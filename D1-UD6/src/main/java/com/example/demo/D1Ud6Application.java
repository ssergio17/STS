package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class D1Ud6Application {

	public static void main(String[] args) {
		SpringApplication.run(D1Ud6Application.class, args);
	}
	
	@Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }

}
