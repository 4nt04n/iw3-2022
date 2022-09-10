package org.mugiwaras.backend.config.profile;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.mugiwaras.backend",
        excludeFilters = {
        })


//Entidades
@EntityScan(basePackages = {
        "org.mugiwaras.backend.model",
        "org.mugiwaras.backend.auth",
        "org.mugiwaras.backend.integration.cli2.model"
})

@Profile("mysqldev")
public class MysqldevScanConfig {
}
