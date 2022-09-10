package org.mugiwaras.backend.config.profile;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.mugiwaras.backend",
        excludeFilters = {
//                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org\\.magm\\.backend\\.integration\\.cli1\\..*" ),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org\\.mugiwaras\\.backend\\.integration\\.cli2\\..*" )
        })


//Entidades
@EntityScan(basePackages = {
        "org.mugiwaras.backend.model",
        "org.mugiwaras.backend.auth"
})

@Profile("mysqlprod")
public class MysqlprodScanConfig {
}
