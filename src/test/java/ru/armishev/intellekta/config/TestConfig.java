package ru.armishev.intellekta.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "ru.armishev.intellekta.jpa")
@ComponentScan(basePackages = "ru.armishev.intellekta.service")
@EntityScan(basePackages = "ru.armishev.intellekta.entity")
public class TestConfig {
}
