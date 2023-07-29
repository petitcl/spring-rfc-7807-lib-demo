package com.example.demo.config;

import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public class ProblemDetailFormatter {

    private final List<ProblemDetailCustomizer> customizers;

    public ProblemDetailFormatter(List<ProblemDetailCustomizer> customizers) {
        this.customizers = customizers;
    }

    public void format(ProblemDetail problemDetails, Throwable throwable, WebRequest request) {
        for (ProblemDetailCustomizer customizer : customizers) {
            customizer.customize(problemDetails, throwable, request);
        }
    }

}
