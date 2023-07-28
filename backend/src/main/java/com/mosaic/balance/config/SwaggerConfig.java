package com.mosaic.balance.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        List<Server> serverList = new ArrayList<>();

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080/api");
//        Server ec2Server = new Server();
//        ec2Server.setUrl("https://");

//        serverList.add(ec2Server);
        serverList.add(localServer);


        return new OpenAPI()
                .servers(serverList)
                .info(new Info().title("Balance-master")
                        .description("API")
                        .version("v0.1"));
    }

    @Bean
    public OpenApiCustomiser customiser() {
        Parameter xff = new Parameter()
                .name("X-Forwarded-For").in(ParameterIn.HEADER.toString())
                .description("X-Forwarded-For : IP addr")
                .schema(new Schema<String>().type("string"))
                .required(false);

        return openApi -> openApi.getPaths().values().forEach(
                operation -> operation.addParametersItem(xff)
        );
    }
}
