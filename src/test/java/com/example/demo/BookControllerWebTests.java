package com.example.demo;

import com.example.demo.books.BooksController;
import com.example.demo.config.WebErrorResponseConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = BooksController.class,
    properties = {
        "spring.mvc.throw-exception-if-no-handler-found=true",
        "spring.resources.add-mappings=false",
    }
)
@Import({
    WebErrorResponseConfig.class,
})
public class BookControllerWebTests {

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
                        "type":"about:blank",
                        "title":"Book not found",
                        "status":404,
                        "detail":"Book with id 4242 not found",
                        "instance":"/v1/books/4242"
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
                        "type":"about:blank",
                        "title":"Book already exists",
                        "status":409,
                        "detail":"Book already exists:1",
                        "instance":"/v1/books"
                    }
                    """, true));
        }

    }

    @DisplayName("Spring Web MVC exceptions")
    @Nested
    class SpringWebMvcExceptions {

        @Test
        public void missingJsonField() throws Exception {
            mockMvc.perform(
                            post("/v1/books")
                                    .contentType("application/json")
                                    .content("""
                                {
                                    "id": 4,
                                    "title": "The Dead Zone"
                                }
                                """)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("""
                    {
                        "type":"about:blank",
                        "title":"Bad Request",
                        "status":400,
                        "detail":"Invalid request content.",
                        "instance":"/v1/books"
                    }
                """, true));
        }

        @Test
        public void methodNotAllowed() throws Exception {
            mockMvc.perform(patch("/v1/books"))
                    .andDo(print())
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(content().json("""
                    {
                        "type":"about:blank",
                        "title":"Method Not Allowed",
                        "status":405,
                        "detail":"Method 'PATCH' is not supported.",
                        "instance":"/v1/books"
                    }
                """, true));
        }

        @Test
        public void routeNotFound() throws Exception {
            mockMvc.perform(get("/v1/unicorns"))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                    {
                        "type":"about:blank",
                        "title":"Not Found",
                        "status":404,
                        "detail":"No endpoint GET /v1/unicorns.",
                        "instance":"/v1/unicorns"
                    }
                """, true));
        }

    }

    @DisplayName("Generic exceptions")
    @Nested
    class GenericExceptions {
        @Test
        public void genericException() throws Exception {
            mockMvc.perform(post("/v1/books/1/read"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("""
                    {
                        "type":"about:blank",
                        "title":"Internal Server Error",
                        "status":500,
                        "detail":"I don't know how to read!",
                        "instance":"/v1/books/1/read"
                    }
                """, true));
        }

    }
}
