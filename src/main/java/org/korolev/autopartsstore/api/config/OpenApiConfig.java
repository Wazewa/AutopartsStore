package org.korolev.autopartsstore.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Autoparts Store API")
                        .version("1.0.0")
                        .description("REST API для интернет-магазина автозапчастей")
                        .contact(new Contact()
                                .email("wazewa73@gmail.com")
                                .name("Королев Иван Михайлович"))
                        )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development")
                ));
    }
}
