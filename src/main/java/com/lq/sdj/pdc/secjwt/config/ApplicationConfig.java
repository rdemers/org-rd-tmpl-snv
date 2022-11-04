package com.lq.sdj.pdc.secjwt.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan({"com.lq.sdj.pdc.secjwt.dto"})
@EnableJpaRepositories({"com.lq.sdj.pdc.secjwt.dao"})
@ComponentScan({"com.lq.sdj.pdc.secjwt.controller"})
public class ApplicationConfig {
    // Rien.
}