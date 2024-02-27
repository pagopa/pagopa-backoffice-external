package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.GetEcResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstitutionsServiceImplTest {

    @Mock
    private BrokerInstitutionsRepository brokerInstitutionsRepository;

    private InstitutionsServiceImpl institutionsService;

    @BeforeEach
    public void setup() {
        Mockito.reset(brokerInstitutionsRepository);
        institutionsService = new InstitutionsServiceImpl(brokerInstitutionsRepository);
    }

    @Test
    public void requestWithValidBrokerCodeShouldReturnValidResponse() {
        when(brokerInstitutionsRepository.findPagedInstitutionsByBrokerCode("VALID_BROKER_CODE", 0, 10)).thenReturn(
                Collections.singletonList(BrokerInstitutionsEntity.builder()
                        .brokerCode("VALID_BROKER_CODE")
                        .institutions(Collections.singletonList(BrokerInstitutionEntity
                                .builder().brokerTaxCode("brokerTaxCode").build())).build())
        );
        GetEcResponse getEcResponse = Assertions.assertDoesNotThrow(() -> institutionsService.getBrokerInstitutions(
                "VALID_BROKER_CODE", 10, 0));
        Assert.notNull(getEcResponse);
        Assert.notNull(getEcResponse.getPageInfo());
        Assert.notNull(getEcResponse.getCreditorInstitutions());
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "VALID_BROKER_CODE",0,10);
    }

    @Test
    public void requestWithMissingBrokerCodeShouldThrowException() {
        Assertions.assertThrows(AppException.class, () -> institutionsService.getBrokerInstitutions(
                "MISSING_BROKER_CODE", 10, 0));
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "MISSING_BROKER_CODE",0,10);
    }

}
