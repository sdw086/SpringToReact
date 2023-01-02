package com.example.reactboot.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@PropertySources({
        @PropertySource(value = {"classpath:config/site.properties"}, encoding = "UTF-8"),
        @PropertySource(value = {"classpath:config/api.properties"}, encoding = "UTF-8")
})

public class PropertyUtil {
    private final Environment environment;

    public String getPropery(String key) {
        return environment.getProperty(key);
    }

    public int getPropertyInt(String key) {
        return Integer.parseInt(environment.getProperty(key));
    }

    public float getPropertyFloat(String key) {
        return Float.parseFloat(environment.getProperty(key));
    }

    public double getPropertyDouble(String key) {
        return Double.parseDouble(environment.getProperty(key));
    }
}
