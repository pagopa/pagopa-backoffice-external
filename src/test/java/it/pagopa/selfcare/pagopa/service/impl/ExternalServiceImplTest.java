package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.*;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerIbansRepository;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import it.pagopa.selfcare.pagopa.repository.CreditorInstitutionIbansRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalServiceImplTest {

    @Mock
    private BrokerInstitutionsRepository brokerInstitutionsRepository;

    @Mock
    private BrokerIbansRepository brokerIbansRepository;
    @Mock
    private CreditorInstitutionIbansRepository creditorInstitutionIbansRepository;
    @Mock
    private UtilComponent utilComponent;

    private ExternalServiceImpl institutionsService;

    @BeforeEach
    void setup() {
        Mockito.reset(brokerInstitutionsRepository, brokerIbansRepository);
        institutionsService = new ExternalServiceImpl(brokerInstitutionsRepository, brokerIbansRepository, creditorInstitutionIbansRepository, utilComponent);
    }

    @Test
    void requestWithValidBrokerCodeShouldReturnValidResponse() {
        when(brokerInstitutionsRepository.findPagedInstitutionsByBrokerCode(
                "VALID_BROKER_CODE", 0, 10)).thenReturn(
                Optional.of(BrokerInstitutionAggregate.builder()
                                .total(10L)
                        .institutionEntities(Collections.singletonList(BrokerInstitutionEntity
                                .builder().brokerTaxCode("brokerTaxCode").build())).build())
        );
        BrokerInstitutionsResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokerInstitutions(
                        "VALID_BROKER_CODE", 10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getCreditorInstitutions());
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "VALID_BROKER_CODE", 0, 10);
    }

    @Test
    void requestWithMissingBrokerCodeShouldThrowException() {
        Assertions.assertThrows(AppException.class, () -> institutionsService.getBrokerInstitutions(
                "MISSING_BROKER_CODE", 10, 0));
        verify(brokerInstitutionsRepository).findPagedInstitutionsByBrokerCode(
                "MISSING_BROKER_CODE", 0, 10);
    }

    @Test
    void requestWithValidDataShouldReturnIbanList() {
        var iban = CreditorInstitutionIbansEntity.builder()
                .iban("IBAN")
                .build();
        var ibanList = List.of(iban);
        when(creditorInstitutionIbansRepository.findAll(0, 10)).thenReturn(List.of(iban));
        when(utilComponent.getTotalDocuments()).thenReturn(1L);
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getCIsIbans(10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        verify(creditorInstitutionIbansRepository).findAll(anyInt(), anyInt());
    }

    @Test
    void requestWithMissingIbansShouldReturnEmptyList() {
        when(creditorInstitutionIbansRepository.findAll(0, 10)).thenReturn(List.of());
        when(utilComponent.getTotalDocuments()).thenReturn(0L);
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(() -> institutionsService.getCIsIbans(10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        Assertions.assertEquals(0, brokerInstitutionsResponse.getIbans().size());
        verify(creditorInstitutionIbansRepository).findAll(anyInt(), anyInt());

    }

    @Test
    void requestOnIbansWithValidBrokerCodeShouldReturnValidResponse() {
        when(brokerIbansRepository.getBrokerIbans(
                "VALID_BROKER_CODE", 0, 10)).thenReturn(
                Optional.of(IbanAggregate.builder()
                        .total(10L)
                        .ibansSlice(Collections.singletonList(IbanEntity
                                .builder().iban("IBAN").build())).build())
        );
        CIIbansResponse brokerInstitutionsResponse = Assertions.assertDoesNotThrow(
                () -> institutionsService.getBrokerIbans(
                        "VALID_BROKER_CODE", 10, 0));
        Assert.notNull(brokerInstitutionsResponse);
        Assert.notNull(brokerInstitutionsResponse.getPageInfo());
        Assert.notNull(brokerInstitutionsResponse.getIbans());
        verify(brokerIbansRepository).getBrokerIbans(
                "VALID_BROKER_CODE", 0, 10);
    }

    @Test
    void requestOnIbansWithMissingBrokerCodeShouldThrowException() {
        Assertions.assertThrows(AppException.class, () -> institutionsService.getBrokerIbans(
                "MISSING_BROKER_CODE", 10, 0));
        verify(brokerIbansRepository).getBrokerIbans(
                "MISSING_BROKER_CODE", 0, 10);
    }


}
