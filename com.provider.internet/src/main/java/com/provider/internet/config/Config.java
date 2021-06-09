package com.provider.internet.config;

import com.provider.internet.controller.filter.AuthorizationFilter;
import com.provider.internet.controller.filter.PRGFilter;
import com.provider.internet.controller.i18n.SupportedLocale;
import com.provider.internet.controller.util.constants.Attributes;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.repository.UserRepository;
import com.provider.internet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class Config {

    private final static String SUPPORTED_LOCALES = "supportedLocales";

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void setUp(){
        servletContext.setAttribute(SUPPORTED_LOCALES,
                SupportedLocale.getSupportedLanguages());
        servletContext.setAttribute(Attributes.USER_LIST,
                new ConcurrentHashMap<String, UserDto>());
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(){
        FilterRegistrationBean<AuthorizationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.addUrlPatterns("/site/user/*", "/site/manager/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<PRGFilter> PRGFilter(){
        FilterRegistrationBean<PRGFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new PRGFilter());
        registrationBean.addUrlPatterns("/site/info");

        return registrationBean;
    }

}