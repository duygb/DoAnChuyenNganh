package com.nlu.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // User
        exposeDirectory("images/book-photos", registry);

    }

    private void exposeDirectory(String logicalPath, ResourceHandlerRegistry registry) {
        Path path = Paths.get(logicalPath);
        String absolutePath = path.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + logicalPath + "/**").addResourceLocations("file:/" + absolutePath + "/");
    }
}
