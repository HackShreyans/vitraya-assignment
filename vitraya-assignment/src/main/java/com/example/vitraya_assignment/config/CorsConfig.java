package com.example.vitraya_assignment.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // Allow all paths
      .allowedOrigins("*") // Allow all origins
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific methods
      .allowedHeaders("*"); // Allow all headers
  }
}
