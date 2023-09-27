package com.sap.epd.common.businesslog.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class BusinessEventConfiguration {

    @Bean
    @ConditionalOnMissingBean
    BusinessEventRegistry businessEventRegistry(){
        return HashMap::new;
    }
}
