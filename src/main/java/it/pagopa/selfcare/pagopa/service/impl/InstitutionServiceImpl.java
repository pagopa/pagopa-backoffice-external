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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
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
    public InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(InstitutionsServiceFilter institutionsServiceFilter){

        List<InstitutionServiceConsent> institutionServiceConsentList;
        Criteria criteria = new Criteria();
        Pageable pageable = PageRequest.of(institutionsServiceFilter.getPage(), institutionsServiceFilter.getPageSize(), Sort.Direction.DESC);
        Page<InstitutionConsentEntity> page;
        Query query = new Query();

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

                query.addCriteria(criteria);

                query.with(pageable);
                // Get all the document matching the criteria created
                List<InstitutionConsentEntity> institutionConsentEntityList = mongoTemplate.find(query, InstitutionConsentEntity.class);

                // Avoid to execute the count query if the size of the list of document returned is less than the size of the page
                page =  PageableExecutionUtils.getPage(
                        institutionConsentEntityList,
                        pageable,
                        () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), InstitutionConsentEntity.class)
                );

                institutionServiceConsentList = institutionConsentEntityList.stream().map(institutionConsentEntity ->
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
                                .page(page.getNumber())
                                .limit(page.getSize())
                                .totalElements(page.getTotalElements())
                                .totalPages((long) page.getTotalPages())
                                .build()
                ).build();

    }



}
