package com.example.operation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@CrossOrigin(origins = "http://localhost:4200")
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/api/v1/**")
	        .allowedOrigins("http://localhost:4200")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowCredentials(true)
	        .exposedHeaders("Access-Control-Allow-Origin");
	}
}





