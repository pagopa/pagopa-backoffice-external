package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public InstitutionServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Retrive a paged list of institution consent filtered by consent type and by starting and ending date
     *
     * @param institutionsServiceFilter
     * @return
     */
    //public InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId serviceId, int page, int size, Consent consent, OffsetDateTime startingDate, OffsetDateTime endingDate){
    public InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(InstitutionsServiceFilter institutionsServiceFilter){

        List<InstitutionServiceConsent> institutionServiceConsentList;
        Page<InstitutionConsentEntity> pageObject;
        Criteria criteria = new Criteria();

        switch(institutionsServiceFilter.getServiceId()){
            case RTP:
                // Add the consentType if is not null
                if(institutionsServiceFilter.getConsent() != null) {
                    criteria.and("consent").is(institutionsServiceFilter.getConsent().name());
                }

                // Add the date criteria filtering
                if(institutionsServiceFilter.getStartingData() != null || institutionsServiceFilter.getEndingDate() != null){
                    Criteria dateCriteria = Criteria.where("consentDate");

                    if(institutionsServiceFilter.getStartingData() != null) {
                        dateCriteria.gte(institutionsServiceFilter.getStartingData().toInstant());
                    }
                    if(institutionsServiceFilter.getEndingDate() != null) {
                        dateCriteria.lte(institutionsServiceFilter.getEndingDate().toInstant());
                    }
                    criteria.andOperator(dateCriteria);
                }


                // Aggregation construction
                Aggregation aggregation = Aggregation.newAggregation(
                        // Take all the match
                        Aggregation.match(criteria),

                        // Divide in 2 one for the total count and the second for the
                        Aggregation.facet()
                                // Count the total
                                .and(Aggregation.count().as("total")).as("metadata")
                                // Get all the data
                                .and(
                                        // Order all the data in DESC order
                                        Aggregation.sort(Sort.Direction.DESC, "consentDate"),
                                        // Managing the pagination
                                        Aggregation.skip((long) institutionsServiceFilter.getPage() * institutionsServiceFilter.getPageSize()),
                                        Aggregation.limit(institutionsServiceFilter.getPageSize())
                                ).as("data")
                );

                // One call to the db
                AggregationResults<Document> results = mongoTemplate.aggregate(
                        aggregation, InstitutionConsentEntity.class, Document.class);

                // Estract the result from the document
                Document uniqueResult = results.getUniqueMappedResult();

                // Get the total number of the elements
                long totalElements = 0;
                List<Document> metadata = (List<Document>) uniqueResult.get("metadata");
                if (metadata != null && !metadata.isEmpty()) {
                    totalElements = metadata.get(0).getInteger("total").longValue();
                }

                // Get the list of document containing the InstitutionConsentEntity and convert them
                List<Document> dataDocs = (List<Document>) uniqueResult.get("data");
                List<InstitutionConsentEntity> entities = dataDocs.stream()
                        .map(doc -> mongoTemplate.getConverter().read(InstitutionConsentEntity.class, doc))
                        .toList();

                // Create the PageImpl object for calculate the page parameter automatically
                pageObject = new PageImpl<>(entities, PageRequest.of(institutionsServiceFilter.getPage(), institutionsServiceFilter.getPageSize()), totalElements);

                institutionServiceConsentList = entities.stream().map(institutionConsentEntity ->
                        InstitutionServiceConsent.builder()
                                .institutionInfo(
                                        InstitutionInfo
                                                .builder()
                                                .taxCode(institutionConsentEntity.getInstitutionTaxCode())
                                                .name(institutionConsentEntity.getName())
                                                .build()
                                )
                                .consentInfo(
                                        ConsentInfo
                                                .builder()
                                                .consent(institutionConsentEntity.getConsent())
                                                .date(institutionConsentEntity.getConsentDate().atOffset(ZoneOffset.UTC))
                                                .build()
                                ).build()).toList();
                break;
            default: throw new AppException(AppError.SERVICE_NOT_HANDLED);
        }


        return  InstitutionsServicesConsentResponse
                .builder()
                .results(
                    institutionServiceConsentList
                )
                .pageInfo(
                        PageInfo.builder()
                                .page(pageObject.getNumber())
                                .limit(pageObject.getSize())
                                .totalElements(pageObject.getTotalElements())
                                .totalPages((long) pageObject.getTotalPages())
                                .build()
                ).build();

    }



}
