package com.project.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerSecurityConfig {

    @Value("${SWAGGER_SERVER}")
    private String server;

    @Bean
    public OpenAPI customizeOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API 🛒")
                        .version("v1.0 🚀")
                        .description("Welcome to the **Ecommerce API MAWA **! 🎉\n\n" +
                                "This API provides a platform for user authentication, including **Login** and **Registration** features. 🚀\n\n" +
                                "Key Points 📌:\n" +
                                "- **Login** and **Register** endpoints do **not require authentication** 🔑.\n" +
                                "- Make sure to **send the correct parameters** when using these endpoints 💻.\n\n" +
                                "🔑 **Important Notes**:\n" +
                                "1. The **Login** endpoint allows users to authenticate and receive a JWT token 🎟️.\n" +
                                "2. The **Register** endpoint enables new users to sign up 📝.\n\n" +
                                "For more details, explore the endpoints below and see the documentation. 📚\n" +
                                "Happy shopping! Jai @JAS 🛍️"
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")
                )
                .addServersItem(new Server()
                        .url(server).description("```")
                );
    }
}
