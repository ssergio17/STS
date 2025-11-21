package com.example.demo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http
			) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		BCryptPasswordEncoder encoder = 
				new BCryptPasswordEncoder();
		UserDetails user =
			 User.builder()
				.username("user")
				.password("$2a$10$mIAs6gpH3DQvv6zrlZZmoO4rPHkjx43oewjsh.J.NJH3dM7rLXXZm")
				.roles("USER")
				.build();
		
		UserDetails sergio =
				 User.builder()
					.username("sergio")
					.password("$2a$10$cZPAvXe0ngTlTpsm4epaFeGrypZwpP6O1Fx2h.LXMKNt9wh3JRVO2")
					.roles("USER")
					.build();

		return new InMemoryUserDetailsManager(user, sergio);
	}
}
