package it.pagopa.selfcare.pagopa.controller;

import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.service.BrokersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrokerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BrokersService brokersService;

    @BeforeEach
    void setUp() {
        when(brokersService.getBrokerInstitutions("11111",10, 0))
                .thenReturn(BrokerInstitutionsResponse.builder().build());
        when(brokersService.getBrokersIbans(10, 0))
                .thenReturn(BrokerIbansResponse.builder().build());
        when(brokersService.getBrokerInstitutions("00000",10,0))
                .thenThrow(new AppException(AppError.BROKER_INSTITUTIONS_NOT_FOUND, "00000"));
    }

    @Test
    void exportCreditorInstitutionsWithValidCodeShouldReturn20XAndData() throws Exception {
        String url = "/brokers/11111/creditor_institutions";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        verify(brokersService).getBrokerInstitutions("11111",10,0);
    }

    @Test
    void exportCreditorInstitutionsWithMissingCodeShouldReturnNotFound() throws Exception {
        String url = "/brokers/00000/creditor_institutions";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"));
        verify(brokersService).getBrokerInstitutions("00000",10,0);
    }

    @Test
    void getBrokerIbansShouldReturn20XAndData() throws Exception {
        String url = "/brokers/ibans";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        verify(brokersService).getBrokersIbans(10,0);
    }

}