package application.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "User-service API", version = "0.1", description = "User-service REST API documentation"
        )
)
public class SwaggerConfiguration {
}
