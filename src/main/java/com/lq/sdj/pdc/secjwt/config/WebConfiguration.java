package com.lq.sdj.pdc.secjwt.config;

import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebConfiguration implements WebMvcConfigurer
{
    public WebConfiguration()
    {
        super();
    }

    @Override // Pour Swagger.
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api()
    {
        final String CST_EXCLUDE_PACKAGE = "org.springframework.boot";

        // Enregistre le contrôleur "swagger".
        // Configure le Docket (servlet de la documentation).
        // Configuration minimale.
        return new Docket(DocumentationType.SWAGGER_2)
                       .select()
                       .apis(Predicate.not(RequestHandlerSelectors.basePackage(CST_EXCLUDE_PACKAGE)))
                       .build();
    }
}