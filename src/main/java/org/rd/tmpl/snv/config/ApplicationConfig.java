package org.rd.tmpl.snv.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan({"org.rd.tmpl.snv.dto"})
@EnableJpaRepositories({"org.rd.tmpl.snv.dao"})
@ComponentScan({"org.rd.tmpl.snv.controller"})
public class ApplicationConfig {
    // Rien.
}