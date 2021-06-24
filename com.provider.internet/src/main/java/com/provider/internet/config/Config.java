package com.provider.internet.config;

import com.provider.internet.controller.filter.PRGFilter;
import com.provider.internet.controller.i18n.SupportedLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Configuration
public class Config {

    private final static String SUPPORTED_LOCALES = "supportedLocales";

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void setUp() {
        servletContext.setAttribute(SUPPORTED_LOCALES,
                SupportedLocale.getSupportedLanguages());
    }


    @Bean
    public FilterRegistrationBean<PRGFilter> PRGFilter() {
        FilterRegistrationBean<PRGFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PRGFilter());
        registrationBean.addUrlPatterns("/site");

        return registrationBean;
    }

}