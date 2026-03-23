package com.emtlabs.emtlabs.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EMT Labs API",
                version = "v1",
                description = "Book search, projections, database view, materialized view, and rental event workflows",
                license = @License(name = "Internal")
        )
)
public class OpenApiDocumentationConfig {
}

