package com.pets.petAfriend.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "validation")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@Getter
@Setter
public class PropertyConfig {

    private Map<String, String> pet;
    private Map<String, String> client;

    public Map<String, String> getAll() {
        Map<String, String> all = new HashMap<>();
        all.putAll(pet);
        all.putAll(client);
        return new HashMap<>(all);
    }
}
