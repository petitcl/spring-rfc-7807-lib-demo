package com.example.demo.config;

import jakarta.annotation.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

public class MessageSourceCustomizer implements ProblemDetailCustomizer {

    @Nullable
    private final MessageSource messageSource;

    public MessageSourceCustomizer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void customize(ProblemDetail problemDetails, Throwable throwable, WebRequest request) {
        if (messageSource != null) {
            final String rawType = messageSource.getMessage(
                    getTypeMessageCode(throwable),
                    null,
                    problemDetails.getType().toString(),
                    request.getLocale()
            );
            problemDetails.setType(URI.create(rawType));

            problemDetails.setTitle(messageSource.getMessage(
                getTitleMessageCode(throwable),
                null,
                problemDetails.getTitle(),
                request.getLocale()
            ));

            problemDetails.setDetail(messageSource.getMessage(
                getDetailsMessageCode(throwable),
                null,
                problemDetails.getDetail(),
                request.getLocale()
            ));
        }
    }

    public static String getTypeMessageCode(Throwable throwable) {
        return "custom.problemDetail.type." + throwable.getClass().getName();
    }

    public static String getTitleMessageCode(Throwable throwable) {
        return "custom.problemDetail.title." + throwable.getClass().getName();
    }

    public static String getDetailsMessageCode(Throwable throwable) {
        return "custom.problemDetail.detail." + throwable.getClass().getName();
    }

}
