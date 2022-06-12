package com.mateocr.enterpriseapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Hardware Store API",
        version = "1.0.0",
        description = "Hardware Store API v1.0.0"))
@EnableReactiveMongoRepositories
@EnableSwagger2
public class EnterpriseappApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseappApplication.class, args);
    }

}
