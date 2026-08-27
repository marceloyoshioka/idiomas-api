package com.pet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Idiomas API - Integração Gemini")
					.version("1.0.2")
					.description("API REST desenvolvida em Spring Boot para auxílio no aprendizado de idiomas utilizando IA generativa.")
				);
	}
}
