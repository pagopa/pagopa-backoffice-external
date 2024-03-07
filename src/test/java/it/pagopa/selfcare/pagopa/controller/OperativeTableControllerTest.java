package it.pagopa.selfcare.pagopa.controller;

import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;
import it.pagopa.selfcare.pagopa.service.OperativeTableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OperativeTableControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OperativeTableService operativeTableServiceMock;

    @Test
    void getPaymentServiceProvidersWithoutQueryParamTest() throws Exception {
        when(operativeTableServiceMock.getOperativeTables(anyString(), anyString())).thenReturn(new OperativeTableResourceList());

        mvc.perform(get("/operative_tables"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getPaymentServiceProvidersWithQueryParamTest() throws Exception {
        when(operativeTableServiceMock.getOperativeTables(anyString(), anyString())).thenReturn(new OperativeTableResourceList());

        mvc.perform(get("/operative_tables")
                        .param("taxCode", "tax-code")
                        .param("businessName", "business name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
