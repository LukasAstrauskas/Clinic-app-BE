package com.sourcery.clinicapp.configuration;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(ourApiInfo());
    }

    private ApiInfo ourApiInfo() {
        return new ApiInfo(
                "Clinic registration app API v0.0.1",
                "Patients registration to physicians app",
                "Our terms of service are pretty loose",
                "Terms of service",

                new Contact("G-Unit team", "www.google.com", "tikras@email.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
