package it.pagopa.selfcare.pagopa.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getInstitutionsServiceConsentShouldReturn20XandData() throws Exception {
        String url = "/institutions/services/RTP/consents";
        mvc.perform(get(url)
                    .param("pageNumber","0")
                    .param("pageSize","1")
                    .param("consent","OPT_IN")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getInstitutionsServiceConsentWithoutRequiredParameterShouldReturn400() throws Exception {
        String url = "/institutions/services/RTP/consents";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }



}
