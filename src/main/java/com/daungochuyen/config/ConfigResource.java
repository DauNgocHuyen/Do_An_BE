package com.daungochuyen.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config resource
 * @author ADMIN
 *
 */
@Configurable
@EnableWebMvc
public class ConfigResource implements WebMvcConfigurer{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/static/**");
		registry.addResourceHandler("/resources/templates/**");
	}
	
	// CORS config
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("*")
	        .allowedOrigins("http://localhost:3010")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true);
    }
	
}