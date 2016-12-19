package com.a2r2.api.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.a2r2.api.rest", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.a2r2\\.api\\.rest\\.security\\.plain\\.Plain\\.*") )
public class A2R2Configuration {

}