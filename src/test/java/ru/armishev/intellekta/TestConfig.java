package ru.armishev.intellekta;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "ru.armishev.intellekta.jpa")
@EntityScan(basePackages = "ru.armishev.intellekta.entity")
public class TestConfig {
}
