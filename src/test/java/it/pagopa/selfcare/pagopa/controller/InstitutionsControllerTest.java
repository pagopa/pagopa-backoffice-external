package it.pagopa.selfcare.pagopa.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InstitutionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InstitutionService institutionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getInstitutionsServiceConsentShouldReturn20XandData() throws Exception {
        String url = "/institutions/services/RTP/consents";

        InstitutionsServicesConsentResponse institutionsServicesConsentResponse = InstitutionsServicesConsentResponse
                .builder()
                .results(
                        List.of(
                                InstitutionServiceConsent
                                        .builder()
                                        .institutionInfo(
                                                InstitutionInfo
                                                        .builder()
                                                        .taxCode("77777777777")
                                                        .name("EC name")
                                                        .build()
                                        )
                                        .consentInfo(
                                                ConsentInfo
                                                        .builder()
                                                        .consent(Consent.OPT_IN)
                                                        .date(OffsetDateTime.now())
                                                        .build()
                                        )
                                        .build()
                        )
                )
                .hasNext(true)
                .build();


        when(institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(any()))
                .thenReturn(institutionsServicesConsentResponse);

        MvcResult mvcResult = mvc.perform(get(url)
                        .param("pageNumber", "0")
                        .param("pageSize", "1")
                        .param("consent", "OPT_IN")
                        .param("toDate", OffsetDateTime.now().toString())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        InstitutionsServicesConsentResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                InstitutionsServicesConsentResponse.class
        );

        Assertions.assertNotNull(response);
        assertTrue(response.isHasNext());

    }

    @Test
    void getInstitutionsServiceConsentWithoutRequiredParameterShouldReturn400() throws Exception {
        String url = "/institutions/services/RTP/consents";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getInstitutionsServiceConsentWithNotAccectablePageSizeShouldReturn400() throws Exception {
        String url = "/institutions/services/RTP/consents";
        mvc.perform(get(url)
                        .param("pageNumber", "0")
                        .param("pageSize", "0")
                        .param("consent", "OPT_IN"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getInstitutionsServiceConsentWithNotAccectablePageNumberShouldReturn400() throws Exception {
        String url = "/institutions/services/RTP/consents";
        mvc.perform(get(url)
                        .param("pageNumber", "-1")
                        .param("pageSize", "1")
                        .param("consent", "OPT_IN"))
                .andExpect(status().is4xxClientError());
    }


}
