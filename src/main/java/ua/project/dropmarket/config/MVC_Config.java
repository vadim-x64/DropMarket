package ua.project.dropmarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVC_Config implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry reg)
    {
        reg.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}