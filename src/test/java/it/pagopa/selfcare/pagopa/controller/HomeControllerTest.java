package it.pagopa.selfcare.pagopa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Value("${server.servlet.context-path}")
    public String basePath;

    @Test
    void noPathExpectRedirectToSwagger() throws Exception {
        String url = "/";
        mvc.perform(get(url))
                .andExpect(redirectedUrl(basePath + "swagger-ui.html"));
    }

    @Test
    void healthCheckShouldReturn200() throws Exception {
        String url = "/info";
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

}
