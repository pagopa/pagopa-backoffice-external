package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.repository.InstitutionServiceRtpConsentRepository;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionServiceRtpConsentRepository repository;

    @Autowired
    public InstitutionServiceImpl(InstitutionServiceRtpConsentRepository repository){
        this.repository = repository;
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
        Pageable pageable = PageRequest.of(institutionsServiceFilter.getPage(), institutionsServiceFilter.getPageSize(), Sort.Direction.DESC, "consentDate");
        Page<InstitutionConsentEntity> page;
        Query query = new Query();
        long count = 0;

        switch(institutionsServiceFilter.getServiceId()){
            case RTP:

                OffsetDateTime startDate = Optional.ofNullable(institutionsServiceFilter.getStartingData()).orElse(OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC));
                OffsetDateTime endDate = institutionsServiceFilter.getEndingDate();
                Consent consent = institutionsServiceFilter.getConsent();

                count = repository.countByDateAndConsent(startDate,endDate,consent);

                institutionServiceConsentList = repository
                        .findByDateAndConsent(startDate,endDate,consent,pageable)
                        .stream()
                        .map(institutionConsentEntity ->
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
                                    )
                                    .build()
                        )
                        .toList();
                break;
            default: throw new AppException(AppError.SERVICE_NOT_HANDLED);
        }

        long totalPages = count/institutionsServiceFilter.getPageSize();

        if((totalPages % institutionsServiceFilter.getPageSize()) != 0) {
          totalPages++;
        }

        return  InstitutionsServicesConsentResponse
                .builder()
                .results(
                    institutionServiceConsentList
                )
                .pageInfo(
                        PageInfo.builder()
                                .page(institutionsServiceFilter.getPage())
                                .limit(institutionsServiceFilter.getPageSize())
                                .totalElements(count)
                                .totalPages(totalPages)
                                .build()
                ).build();

    }



}
