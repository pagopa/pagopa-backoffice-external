package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.repository.InstitutionServiceRtpConsentRepository;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionServiceRtpConsentRepository repository;

    @Autowired
    public InstitutionServiceImpl(InstitutionServiceRtpConsentRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrive a paged list of institution consent filtered by consent type and by starting and ending date
     *
     * @param institutionsServiceFilter
     * @return
     */
    public InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(InstitutionsServiceFilter institutionsServiceFilter) {

        List<InstitutionServiceConsent> institutionServiceConsentList;
        //fetch requested records + 1. the extra element will be used to determine if more records are available for the paginated query
        Pageable pageable = PageRequest.of(institutionsServiceFilter.getPage(), institutionsServiceFilter.getPageSize() + 1, Sort.Direction.DESC, "consentDate");
        boolean hasNext;
        switch (institutionsServiceFilter.getServiceId()) {
            case RTP:

                Instant startDate = Optional.ofNullable(institutionsServiceFilter.getStartingDate()).orElse(OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)).toInstant();
                Instant endDate = institutionsServiceFilter.getEndingDate().toInstant();
                Consent consent = institutionsServiceFilter.getConsent();
                List<InstitutionConsentEntity> entities = repository
                        .findByDateAndConsent(startDate, endDate, consent, pageable);
                hasNext = entities.size() > institutionsServiceFilter.getPageSize();
                institutionServiceConsentList = entities
                        .stream()
                        .limit(institutionsServiceFilter.getPageSize())
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
            default:
                throw new AppException(AppError.SERVICE_NOT_HANDLED);
        }

        return InstitutionsServicesConsentResponse
                .builder()
                .results(
                        institutionServiceConsentList
                )
                .hasNext(hasNext)
                .build();

    }

}
