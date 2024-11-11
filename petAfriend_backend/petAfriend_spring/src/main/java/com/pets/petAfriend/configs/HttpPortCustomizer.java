package com.pets.petAfriend.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class HttpPortCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Autowired
    private Environment environment;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(environment.getProperty("server_port", Integer.class));
    }
}
