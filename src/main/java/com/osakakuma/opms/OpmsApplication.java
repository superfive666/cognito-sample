package com.osakakuma.opms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@OpenAPIDefinition(servers = {
		@Server(url = "https://opms.myosakakuma.com/", description = "API Server"),
		@Server(url = "http://localhost:8080/", description = "Localhost For Testing")
})
@SpringBootApplication
@EnableConfigurationProperties
public class OpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpmsApplication.class, args);
	}

}
