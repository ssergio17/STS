package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PokemonService {
	 private final WebClient webClient;

	    public PokemonService(WebClient webClient) {
	        this.webClient = webClient;
	    }

	    public Pokemon getPokemonByName(String name) {
	        return webClient
	                .get()
	                .uri("/pokemon/{name}", name)
	                .retrieve()
	                .bodyToMono(Pokemon.class)
	                .block();
	    }
}
