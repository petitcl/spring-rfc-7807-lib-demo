package com.example.demo;

import com.example.demo.books.BooksController;
import com.example.demo.config.WebErrorResponseConfig;
import com.example.demo.userconfig.UserConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = BooksController.class,
    properties = {
        "spring.main.web-application-type=servlet",
        "spring.mvc.throw-exception-if-no-handler-found=true",
        "spring.resources.add-mappings=false",
    }
)
@Import({
    UserConfig.class,
    WebErrorResponseConfig.class,
})
public class CustomizerWebTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Web Exposed exceptions")
    @Nested
    class WebExposedExceptions {
        @Test
        public void exposedNotFoundException() throws Exception {
            mockMvc.perform(get("/v1/books/4242"))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                    {
                        "type":"https://example.com/book-not-found",
                        "title":"Book not found",
                        "status":404,
                        "detail":"Book with id 4242 not found",
                        "instance":"/v1/books/4242",
                        "metadata":"some metadata"
                    }
                    """, true));
        }

        @Test
        public void exposedConflictException() throws Exception {
            mockMvc.perform(
                            post("/v1/books")
                                    .contentType("application/json")
                                    .content("""
                        {
                            "id": 1,
                            "title": "The Stand",
                            "isbn": "978-0307743688"
                        }
                        """)
                    )
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(content().json("""
                    {
                        "type":"https://example.com/book-already-exists",
                        "title":"Book already exists",
                        "status":409,
                        "detail":"Book already exists:1",
                        "instance":"/v1/books",
                        "metadata":"some metadata"
                    }
                    """, true));
        }

    }

    @DisplayName("Spring Web MVC exceptions")
    @Nested
    class SpringWebMvcExceptions {
        @Test
        public void routeNotFoundWithCustomFields() throws Exception {
            mockMvc.perform(get("/v1/unicorns"))
                    .andExpect(status().isNotFound())
                    .andDo(print())
                    .andExpect(content().json("""
                    {
                        "type":"https://example.com/route-not-found",
                        "title":"Not Found title - overridden",
                        "status":404,
                        "detail":"Not Found detail - overridden",
                        "instance":"/v1/unicorns",
                        "metadata":"some metadata"
                    }
                """, true));
        }
    }

    @DisplayName("Generic exceptions")
    @Nested
    class GenericExceptions {
        @Test
        public void genericExceptionWithCustomFields() throws Exception {
            mockMvc.perform(post("/v1/books/1/read"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("""
                    {
                        "type":"about:blank",
                        "title":"Internal Server Error",
                        "status":500,
                        "detail":"I don't know how to read!",
                        "instance":"/v1/books/1/read",
                        "metadata":"some metadata"
                    }
                """, true));
        }

        @Test
        public void bookPublishingException() throws Exception {
            mockMvc.perform(post("/v1/books/1/publish"))
                    .andDo(print())
                    .andExpect(status().isIAmATeapot())
                    .andExpect(content().json("""
                    {
                        "type":"https://example.com/i-am-a-teapot",
                        "title":"I'm a teapot",
                        "status":418,
                        "detail":"I don't know how to publish book 1",
                        "instance":"/v1/books/1/publish",
                        "metadata":"some metadata"
                    }
                """, true));
        }
    }
}
