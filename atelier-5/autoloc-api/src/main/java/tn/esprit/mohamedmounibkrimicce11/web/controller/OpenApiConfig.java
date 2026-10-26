package tn.esprit.mohamedmounibkrimicce11.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI autoLocOpenApi() {
        return new OpenAPI().info(new Info().title("AutoLoc API")
                .description("API REST AutoLoc : agences, véhicules, clients, réservations, contrats et simulation de prix")
                .version("1.0.0"));
    }
}
