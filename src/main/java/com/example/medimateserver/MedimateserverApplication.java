package com.example.medimateserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class
MedimateserverApplication {

	@Value("${app.base-url}")
	private String baseUrl;

	public static void main(String[] args) {
		SpringApplication.run(MedimateserverApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Cho phép tất cả các route
						//.allowedOrigins("http://localhost:3000")
						.allowedOriginPatterns(
								"https://*.ngrok-free.app", // Cho phép tất cả các subdomain của ngrok-free.app
								"http://*.example.com", // Cho phép tất cả các subdomain của example.com
								"http://localhost:*" // Cho phép tất cả các cổng trên localhost
						)// Cho phép origin này
						.allowedMethods("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS") // Các HTTP method được phép
						.allowedHeaders("*") // Cho phép mọi header
						.allowCredentials(true); // Nếu có sử dụng cookie
			}
		};
	}

}

