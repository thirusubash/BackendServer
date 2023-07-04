package com.gksvp.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // Configuration class implementing WebMvcConfigurer

    public void addViewControllers(ViewControllerRegistry registry) {
        // Overrides the addViewControllers method to register view controllers

        registry.addViewController("/home").setViewName("home");
        // Registers a view controller for the URL "/home" and sets the view name "home"

        registry.addViewController("/").setViewName("home");
        // Registers a view controller for the root URL "/" and sets the view name
        // "home"

        registry.addViewController("/hello").setViewName("hello");
        // Registers a view controller for the URL "/hello" and sets the view name
        // "hello"

        registry.addViewController("/login").setViewName("login");
        // Registers a view controller for the URL "/login" and sets the view name
        // "login"
    }
}
