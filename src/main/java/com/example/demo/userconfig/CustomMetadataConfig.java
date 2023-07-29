package com.example.demo.userconfig;

import com.example.demo.config.ProblemDetailCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;

@Configuration
public class CustomMetadataConfig {

    public static class MetadataCustomizer implements ProblemDetailCustomizer {
        @Override
        public void customize(ProblemDetail problemDetails, Throwable throwable, WebRequest request) {
            problemDetails.setProperty("metadata", "some metadata");
        }
    }

    @Bean
    public MetadataCustomizer metadataCustomizer() {
        return new MetadataCustomizer();
    }
}
