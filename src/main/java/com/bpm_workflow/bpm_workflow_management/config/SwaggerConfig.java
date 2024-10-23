package com.bpm_workflow.bpm_workflow_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BPM Workflow Management API")
                        .description("BPM Workflow Management API Documentation")
                        .version("1.0.0"));
    }

}
