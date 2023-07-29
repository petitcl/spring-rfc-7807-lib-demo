package com.example.demo.userconfig;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Map;

@Configuration
public class CustomMessageSourceConfig {

    public static class CustomMessageSource implements MessageSource {

        private final Map<String, String> messages = Map.of(
            "custom.problemDetail.type.org.springframework.web.servlet.NoHandlerFoundException", "https://example.com/route-not-found",
            "custom.problemDetail.title.org.springframework.web.servlet.NoHandlerFoundException", "Not Found title - overridden",
            "custom.problemDetail.detail.org.springframework.web.servlet.NoHandlerFoundException", "Not Found detail - overridden",
            "custom.problemDetail.type.com.example.demo.books.BookNotFoundException", "https://example.com/book-not-found",
            "custom.problemDetail.type.com.example.demo.books.BookAlreadyExistsException", "https://example.com/book-already-exists"
        );

        @Override
        public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
            return messages.getOrDefault(code, defaultMessage);
        }

        @Override
        public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
            return messages.getOrDefault(code, null);
        }

        @Override
        public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
            return null;
        }

        @Override
        public String toString() {
            return "CustomMessageSource";
        }
    }

    @Bean
    public CustomMessageSource messageSource() {
        return new CustomMessageSource();
    }
}
