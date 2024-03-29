package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerIbanEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerIbansRepository;
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
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalServiceImplTest {

    @Mock
    private BrokerInstitutionsRepository brokerInstitutionsRepository;

    @Mock
    private BrokerIbansRepository brokerIbansRepository;

    private ExternalServiceImpl institutionsService;

    @BeforeEach
    void setup() {
        Mockito.reset(brokerInstitutionsRepository, brokerIbansRepository);
        institutionsService = new ExternalServiceImpl(brokerInstitutionsRepository, brokerIbansRepository);
    }

    @Test
    void requestWithValidBrokerCodeShouldReturnValidResponse() {
        when(brokerInstitutionsRepository.findPagedInstitutionsByBrokerCode(
                "VALID_BROKER_CODE", 0, 10)).thenReturn(
                Optional.of(BrokerInstitutionsEntity.builder()
                        .brokerCode("VALID_BROKER_CODE")
                        .institutions(Collections.singletonList(BrokerInstitutionEntity
                                .builder().brokerTaxCode("brokerTaxCode").build())).build())
        );
        BrokerInstitutionsResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokerInstitutions(
                "VALID_BROKER_CODE", 10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getCreditorInstitutions());
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "VALID_BROKER_CODE",0,10);
    }

    @Test
    void requestWithMissingBrokerCodeShouldThrowException() {
        Assertions.assertThrows(AppException.class, () -> institutionsService.getBrokerInstitutions(
                "MISSING_BROKER_CODE", 10, 0));
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "MISSING_BROKER_CODE",0,10);
    }

    @Test
    void requestWithValidDataShouldReturnIbanList() {
        when(brokerIbansRepository.getMergedIbans(
                0, 10)).thenReturn(
                Optional.of(BrokerIbansEntity.builder()
                        .ibans(Collections.singletonList(BrokerIbanEntity
                                .builder().iban("IBAN").build())).build())
        );
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokersIbans(10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        verify(brokerIbansRepository).getMergedIbans(
                0,10);
    }

    @Test
    void requestWithMissingIbansShouldReturnEmptyList() {
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokersIbans(10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        Assertions.assertEquals(0,
                brokerInstitutionsResponse.getIbans().size());
        verify(brokerIbansRepository).getMergedIbans(
                0,10);
    }

    @Test
    void requestOnIbansWithValidBrokerCodeShouldReturnValidResponse() {
        when(brokerIbansRepository.getBrokerIbans(
                "VALID_BROKER_CODE", 0, 10)).thenReturn(
                Optional.of(BrokerIbansEntity.builder()
                        .brokerCode("VALID_BROKER_CODE")
                        .ibans(Collections.singletonList(BrokerIbanEntity
                                .builder().iban("IBAN").build())).build())
        );
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokerIbans(
                        "VALID_BROKER_CODE", 10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        verify(brokerIbansRepository).getBrokerIbans(
                "VALID_BROKER_CODE",0,10);
    }

    @Test
    void requestOnIbansWithMissingBrokerCodeShouldThrowException() {
        Assertions.assertThrows(AppException.class, () -> institutionsService.getBrokerIbans(
                "MISSING_BROKER_CODE", 10, 0));
        verify(brokerIbansRepository).getBrokerIbans(
                "MISSING_BROKER_CODE",0,10);
    }



}
