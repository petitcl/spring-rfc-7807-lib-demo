package com.example.demo.config;

import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.WebRequest;

/**
 * Base interface for classes whose goal is to customize the {@link ProblemDetail} object.
 * <p>
*  For example, this can be used to add additional fields to the {@link ProblemDetail} object.
 */
public interface ProblemDetailCustomizer {

    void customize(ProblemDetail problemDetails, Throwable throwable, WebRequest request);

}
