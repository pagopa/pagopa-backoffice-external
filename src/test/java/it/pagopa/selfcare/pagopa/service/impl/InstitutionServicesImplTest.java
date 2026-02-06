package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.exception.AppException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstitutionServicesImplTest {

    @Mock
    MongoTemplate mongoTemplate;

    @Captor
    private ArgumentCaptor<List<InstitutionConsentEntity>> entityCaptor;

    private InstitutionServiceImpl institutionService;

    @BeforeEach
    void setup(){
        Mockito.reset(mongoTemplate);
        institutionService = new InstitutionServiceImpl(mongoTemplate);
    }

    @Test
    void requestWithValidInstCodeConsentAndAllParams_ShouldReturnValidResponse(){
        // Create the mock return
        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);
        OffsetDateTime endingDate = OffsetDateTime.of(2026,2,1,0,0,0,0, ZoneOffset.UTC);
        List<InstitutionConsentEntity> institutionConsentEntityList = List.of(
                InstitutionConsentEntity
                        .builder()
                        .institutionTaxCode("777")
                        .name("test")
                        .consentDate(startingDate)
                        .id("id")
                        .consent(Consent.OPT_IN)
                        .build()
        );

        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);
        when(mongoTemplate.find(any(), (Class<InstitutionConsentEntity>) any())).thenReturn(institutionConsentEntityList);

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId.RTP,0,1,Consent.OPT_IN,startingDate,endingDate);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

        // Verify the mocked object is used and capture the returned value
        verify(mongoTemplate, times(1)).find( queryCaptor.capture(),(Class<InstitutionConsentEntity>) any());
        // Get the created query and the BSON document for check if all the param are present
        Query capturedQuery = queryCaptor.getValue();
        Document queryDoc = capturedQuery.getQueryObject();
        System.out.println("Query capture: "+queryDoc.toJson());
        // Check the query params
        assertEquals(queryDoc.get("consent"),Consent.OPT_IN.toString());
        assertEquals(queryDoc.get("consentDate", Document.class).get("$gte"),startingDate.toString());
        assertEquals(queryDoc.get("consentDate", Document.class).get("$lte"),endingDate.toString());
        assertEquals(2, queryDoc.size());

        // Check the value of the returned object
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDate,response.getResults().get(0).getConsentInfo().getDate());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());

    }

    @Test
    void requestWithValidInstCodeConsentAndStartingData_ShouldReturnValidResponse(){
        // Create the mock return
        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);

        List<InstitutionConsentEntity> institutionConsentEntityList = List.of(
                InstitutionConsentEntity
                        .builder()
                        .institutionTaxCode("777")
                        .name("test")
                        .consentDate(startingDate)
                        .id("id")
                        .consent(Consent.OPT_IN)
                        .build()
        );

        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);
        when(mongoTemplate.find(any(), (Class<InstitutionConsentEntity>) any())).thenReturn(institutionConsentEntityList);

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId.RTP,0,1,Consent.OPT_IN,startingDate,null);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

        // Verify the mocked object is used and capture the returned value
        verify(mongoTemplate, times(1)).find( queryCaptor.capture(),(Class<InstitutionConsentEntity>) any());

        // Get the created query and the BSON document for check if all the param are present
        Query capturedQuery = queryCaptor.getValue();
        Document queryDoc = capturedQuery.getQueryObject();
        System.out.println("Query capture: "+queryDoc.toJson());
        // Check the query params
        assertEquals(queryDoc.get("consent"),Consent.OPT_IN.toString());
        assertEquals(queryDoc.get("consentDate", Document.class).get("$gte"),startingDate.toString());
        assertEquals(2, queryDoc.size());


        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDate,response.getResults().get(0).getConsentInfo().getDate());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }

    @Test
    void requestWithValidInstCodeConsentAndEndingData_ShouldReturnValidResponse(){
        // Create the mock return
        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);

        List<InstitutionConsentEntity> institutionConsentEntityList = List.of(
                InstitutionConsentEntity
                        .builder()
                        .institutionTaxCode("777")
                        .name("test")
                        .consentDate(startingDate)
                        .id("id")
                        .consent(Consent.OPT_IN)
                        .build()
        );

        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);
        when(mongoTemplate.find(any(), (Class<InstitutionConsentEntity>) any())).thenReturn(institutionConsentEntityList);

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId.RTP,0,1,Consent.OPT_IN,null,startingDate);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

        // Verify the mocked object is used and capture the returned value
        verify(mongoTemplate, times(1)).find( queryCaptor.capture(),(Class<InstitutionConsentEntity>) any());

        // Get the created query and the BSON document for check if all the param are present
        Query capturedQuery = queryCaptor.getValue();
        Document queryDoc = capturedQuery.getQueryObject();
        System.out.println("Query capture: "+queryDoc.toJson());
        // Check the query params
        assertEquals(queryDoc.get("consent"),Consent.OPT_IN.toString());
        assertEquals(queryDoc.get("consentDate", Document.class).get("$lte"),startingDate.toString());
        assertEquals(2, queryDoc.size());


        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDate,response.getResults().get(0).getConsentInfo().getDate());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }

    @Test
    void requestWithValidInstCode_ShouldReturnValidResponse(){
        // Create the mock return
        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);

        List<InstitutionConsentEntity> institutionConsentEntityList = List.of(
                InstitutionConsentEntity
                        .builder()
                        .institutionTaxCode("777")
                        .name("test")
                        .consentDate(startingDate)
                        .id("id")
                        .consent(Consent.OPT_IN)
                        .build()
        );

        when(mongoTemplate.count(any(),(Class<InstitutionConsentEntity>) any())).thenReturn(1L);
        when(mongoTemplate.find(any(), (Class<InstitutionConsentEntity>) any())).thenReturn(institutionConsentEntityList);

        // Call the methode tested
        InstitutionsServicesConsentResponse response = institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId.RTP,0,1,null,null,null);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);

        // Verify the mocked object is used and capture the returned value
        verify(mongoTemplate, times(1)).find( queryCaptor.capture(),(Class<InstitutionConsentEntity>) any());

        // Get the created query and the BSON document for check if all the param are present
        Query capturedQuery = queryCaptor.getValue();
        Document queryDoc = capturedQuery.getQueryObject();
        // Check the query params, in this case the size is zero because no parameters are setted
        assertEquals(0, queryDoc.size());


        // Check the value of the returned object, should be only one element per page
        assertEquals(1,response.getResults().size());
        assertEquals("777",response.getResults().get(0).getInstitutionInfo().getTaxCode());
        assertEquals("test",response.getResults().get(0).getInstitutionInfo().getName());
        assertEquals(Consent.OPT_IN,response.getResults().get(0).getConsentInfo().getConsent());
        assertEquals(startingDate,response.getResults().get(0).getConsentInfo().getDate());
        // Check page detail
        assertEquals(1,response.getPageInfo().getTotalPages());
        assertEquals(0,response.getPageInfo().getPage());
        assertEquals(1,response.getPageInfo().getTotalElements());
    }


    @Test
    void requestWithInvalidServiceCode_ShouldRaiseAnException(){
        // Create the mock return
        OffsetDateTime startingDate = OffsetDateTime.of(2026,1,1,0,0,0,0, ZoneOffset.UTC);

        // Raise an exception if the value is null
        Assertions.assertThrows(AppException.class, () ->
                institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(null,0,1,null,null,null));

        // Raise an exception if the value is UKNOWN serviceId
        Assertions.assertThrows(AppException.class, () ->
                institutionService.getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId.UNKNOWN,0,1,null,null,null));

    }


}
