package ru.betnews.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by dell on 14.04.2018.
 */
@Configuration
@ComponentScan(basePackages = {"ru.betnews"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION, value = EnableWebMvc.class)}
)
public class RootConfig {
}
