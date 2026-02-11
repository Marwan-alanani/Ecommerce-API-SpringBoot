package com.marwan.ecommerce.config;

import com.marwan.ecommerce.controller.common.converter.CaseInsensitiveEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addConverterFactory(new CaseInsensitiveEnumConverterFactory());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Allows any origin
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
