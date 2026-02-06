package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public InstitutionServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    /**
     *  Retrive a paged list of institution consent filtered by consent type and by starting and ending date
     *
     * @param serviceId
     * @param page
     * @param size
     * @param consent
     * @param startingDate
     * @param endingDate
     * @return
     */
    public InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(ServiceId serviceId, int page, int size, Consent consent, OffsetDateTime startingDate, OffsetDateTime endingDate){

        Pageable pageable = PageRequest.of(page, size);
        List<InstitutionServiceConsent> institutionServiceConsentList;
        PageImpl<InstitutionConsentEntity> pages;
        long count = 0;
        Query query = new Query();

        switch(serviceId){
            case RTP:
                // Add the consentType if is not null
                if(consent != null) {
                    query.addCriteria(Criteria.where("consent").is(consent.toString()));
                }

                // Add the date criteria filtering
                if(startingDate != null || endingDate != null){
                    Criteria dateCriteria = Criteria.where("consentDate");

                    if(startingDate != null) {
                        dateCriteria.gte(startingDate.toString());
                    }
                    if(endingDate != null) {
                        dateCriteria.lte(endingDate.toString());
                    }
                    query.addCriteria(dateCriteria);
                }

                // Count the result for the pagination
                count = mongoTemplate.count(query, InstitutionConsentEntity.class);
                // Add pagination to the query
                query.with(pageable);

                List<InstitutionConsentEntity> institutionConsentEntityList = mongoTemplate.find(query, InstitutionConsentEntity.class);

                pages = new PageImpl<>(institutionConsentEntityList,pageable, count);

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
            case UNKNOWN:
            default: throw new AppException(AppError.INSTITUTIONS_SERVICE_CONSENTS_NOT_FOUND);
        }


        return  InstitutionsServicesConsentResponse
                .builder()
                .results(
                    institutionServiceConsentList
                )
                .pageInfo(
                        PageInfo.builder()
                                .page(pages.getNumber())
                                .limit(pages.getSize())
                                .totalElements(pages.getTotalElements())
                                .totalPages(count)
                                .build()
                ).build();

    }



}
