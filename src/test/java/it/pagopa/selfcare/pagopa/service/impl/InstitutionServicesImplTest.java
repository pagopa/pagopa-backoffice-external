package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServicesImplTest {

    @Mock
    MongoTemplate mongoTemplate;

    private InstitutionServiceImpl institutionService;

    @BeforeEach
    void setup(){
        Mockito.reset(mongoTemplate);
        institutionService = new InstitutionServiceImpl(mongoTemplate);
    }

    @Test
    void requestWithValidInstCodeConsentAndAllParams_ShouldReturnValidResponse(){

        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);
        OffsetDateTime endingDate = OffsetDateTime.of(2026,2,1,0,0,0,0, ZoneOffset.UTC);

        Instant startingDateEnt = Instant.now();
        InstitutionConsentEntity mockEntity =  InstitutionConsentEntity
                .builder()
                .institutionTaxCode("777")
                .name("test")
                .consentDate(startingDateEnt)
                .id("id")
                .consent(Consent.OPT_IN)
                .build();

        when(mongoTemplate.find(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(List.of(mockEntity));
        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);

        InstitutionsServiceFilter institutionsServiceFilter = InstitutionsServiceFilter.builder()
                .endingDate(endingDate)
                .startingData(startingDate)
                .page(0)
                .pageSize(1)
                .serviceId(ServiceId.RTP)
                .consent(Consent.OPT_IN)
                .build();

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(institutionsServiceFilter);

        // Check the value of the returned object
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDateEnt.toString(),response.getResults().get(0).getConsentInfo().getDate().toString());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());

    }

    @Test
    void requestWithValidInstCodeConsentAndStartingData_ShouldReturnValidResponse(){

        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);

        Instant startingDateEnt = Instant.now();
        InstitutionConsentEntity mockEntity =  InstitutionConsentEntity
                .builder()
                .institutionTaxCode("777")
                .name("test")
                .consentDate(startingDateEnt)
                .id("id")
                .consent(Consent.OPT_IN)
                .build();

        when(mongoTemplate.find(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(List.of(mockEntity));
        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);

        InstitutionsServiceFilter institutionsServiceFilter = InstitutionsServiceFilter.builder()
                .endingDate(null)
                .startingData(startingDate)
                .page(0)
                .pageSize(1)
                .serviceId(ServiceId.RTP)
                .consent(Consent.OPT_IN)
                .build();

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(institutionsServiceFilter);

        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDateEnt.toString(),response.getResults().get(0).getConsentInfo().getDate().toString());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }

    @Test
    void requestWithValidInstCodeConsentAndEndingData_ShouldReturnValidResponse(){

        OffsetDateTime endingDate = OffsetDateTime.of(2026,2,1,0,0,0,0, ZoneOffset.UTC);

        Instant startingDateEnt = Instant.now();
        InstitutionConsentEntity mockEntity =  InstitutionConsentEntity
                .builder()
                .institutionTaxCode("777")
                .name("test")
                .consentDate(startingDateEnt)
                .id("id")
                .consent(Consent.OPT_IN)
                .build();

        when(mongoTemplate.find(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(List.of(mockEntity));
        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);

        InstitutionsServiceFilter institutionsServiceFilter = InstitutionsServiceFilter.builder()
                .endingDate(endingDate)
                .startingData(null)
                .page(0)
                .pageSize(1)
                .serviceId(ServiceId.RTP)
                .consent(Consent.OPT_IN)
                .build();

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(institutionsServiceFilter);

        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDateEnt.toString(),response.getResults().get(0).getConsentInfo().getDate().toString());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }

    @Test
    void requestWithValidInstCode_ShouldReturnValidResponse(){

        Instant startingDateEnt = Instant.now();
        InstitutionConsentEntity mockEntity =  InstitutionConsentEntity
                .builder()
                .institutionTaxCode("777")
                .name("test")
                .consentDate(startingDateEnt)
                .id("id")
                .consent(Consent.OPT_IN)
                .build();

        when(mongoTemplate.find(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(List.of(mockEntity));
        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);

        InstitutionsServiceFilter institutionsServiceFilter = InstitutionsServiceFilter.builder()
                .endingDate(null)
                .startingData(null)
                .page(0)
                .pageSize(1)
                .serviceId(ServiceId.RTP)
                .consent(Consent.OPT_IN)
                .build();

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(institutionsServiceFilter);

        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDateEnt.toString(),response.getResults().get(0).getConsentInfo().getDate().toString());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }


    @Test
    void requestWithInvalidServiceCode_ShouldRaiseAnException(){
        InstitutionsServiceFilter institutionsServiceFilter = InstitutionsServiceFilter.builder()
                .endingDate(null)
                .startingData(null)
                .page(0)
                .pageSize(1)
                .serviceId(null)
                .consent(null)
                .build();

        // Raise an exception if the value is null
        Assertions.assertThrows(NullPointerException.class, () ->
                institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(institutionsServiceFilter));

    }


}
