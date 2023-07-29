package com.example.demo.config;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebErrorResponseConfig {

    @Nullable
    private final MessageSource messageSource;

    public WebErrorResponseConfig(@Autowired(required = false) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public ProblemDetailFormatter problemDetailFormatter(List<ProblemDetailCustomizer> problemDetailCustomizers) {
        final List<ProblemDetailCustomizer> customizers = new ArrayList<>(problemDetailCustomizers);
        customizers.add(new MessageSourceCustomizer(messageSource));
        return new ProblemDetailFormatter(customizers);
    }

    @Order(1000)
    @Bean
    public WebExceptionHandler webExceptionHandler(ProblemDetailFormatter formatter) {
        return new WebExceptionHandler(formatter, messageSource) {};
    }

    @Order(2000)
    @Bean
    public GenericExceptionHandler genericExceptionHandler(ProblemDetailFormatter formatter) {
        return new GenericExceptionHandler(formatter) {};
    }

}
