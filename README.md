# spring-rfc-7807-lib-demo
Demo for a Spring lib that allows to customise RFC 7807 error responses.

## Introduction
This repo contains a sample Spring Boot 3 application that exposes a web server with a sample domain (books).
The sample domain can throw different types of exceptions that are mapped to RFC 7807 error responses.
There is a lib located in the `com.example.demo.config` package that contains Spring config that allow to easily customise error responses
and make sure all exceptions caught are transformed to the RFC 7807 format.

As a reminder, the RFC 7807 defines that errors returned by JSON REST APIs should have the following formats:

```json
{
  "type":"https://example.com/probs/internal-server-error",
  "title":"Internal Server Error",
  "status":500,
  "detail":"Some error occurred",
  "instance":"/v1/books"
}
```

## Features
- Transform all generic exceptions to RFC 7807 format
- Transform all Spring Web exceptions (eg: route not found, method not allowed, etc...) to RFC 7807 format
- Allow to provide beans to customize all error responses, for example for adding custom fields (eg: metadata, request id, etc...)
- Allow to create custom exception handlers that can be used to transform specific exceptions to RFC 7807 format