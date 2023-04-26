package com.example.coreweb.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "PARKING HERE",
                description = "주차장의 모든것 api명세",
                version = "1.0.0"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi api() {

        return GroupedOpenApi.builder()
                .group("OpenApiController")
                .packagesToScan("com.example")
                .build();
    }
}