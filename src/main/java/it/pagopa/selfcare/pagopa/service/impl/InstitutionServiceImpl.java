package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.repository.InstitutionsRepository;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionsRepository institutionsRepository;

    /**
     * Return the InstitutionsServicesConsentResponse containing all the InstitutionServiceConsent of the selected page.
     * Get from the repository an already paged list of the element and convert to the model used by InstitutionsServicesConsentResponse.
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public InstitutionsServicesConsentResponse getIstitutionServiceConsent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InstitutionConsentEntity> institutionConsentEntityPages = this.institutionsRepository.getInstitutionConsents(pageable);

        List<InstitutionServiceConsent> institutionServiceConsentList = institutionConsentEntityPages.map(institutionConsentEntity ->
                InstitutionServiceConsent.builder()
                .institutionInfo(
                    InstitutionInfo
                            .builder()
                            .taxCode(institutionConsentEntity.getInstitution_tax_code())
                            .name(institutionConsentEntity.getName())
                            .build()
                )
                .consentInfo(
                        ConsentInfo
                                .builder()
                                .consent(institutionConsentEntity.getConsent())
                                .date(institutionConsentEntity.getConsentDate())
                                .build()
                ).build()).toList();

        return InstitutionsServicesConsentResponse
                .builder()
                .results(
                        institutionServiceConsentList
                )
                .pageInfo(
                        PageInfo.builder()
                                .page(institutionConsentEntityPages.getNumber())
                                .limit(institutionConsentEntityPages.getSize())
                                .totalElements(institutionConsentEntityPages.getTotalElements())
                                .totalPages((long) institutionConsentEntityPages.getTotalPages())
                                .build()
                ).build();
    }

    /**
     * Return the InstitutionsServicesConsentResponse containing all the InstitutionServiceConsent of the selected page
     * filtered by a starting date and ending date.
     * Get from the repository an already paged list of the element and convert to the model used by InstitutionsServicesConsentResponse.
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public InstitutionsServicesConsentResponse getIstitutionServiceConsentFilteredByDates(int page, int size, OffsetDateTime startingDate, OffsetDateTime endingDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InstitutionConsentEntity> institutionConsentEntityPages = this.institutionsRepository.findInstitutionConsentsByConsentDateBetween(startingDate, endingDate, pageable);

        List<InstitutionServiceConsent> institutionServiceConsentList = institutionConsentEntityPages.map(institutionConsentEntity ->
                InstitutionServiceConsent.builder()
                        .institutionInfo(
                                InstitutionInfo
                                        .builder()
                                        .taxCode(institutionConsentEntity.getInstitution_tax_code())
                                        .name(institutionConsentEntity.getName())
                                        .build()
                        )
                        .consentInfo(
                                ConsentInfo
                                        .builder()
                                        .consent(institutionConsentEntity.getConsent())
                                        .date(institutionConsentEntity.getConsentDate())
                                        .build()
                        ).build()).toList();

        return InstitutionsServicesConsentResponse
                .builder()
                .results(
                        institutionServiceConsentList
                )
                .pageInfo(
                        PageInfo.builder()
                                .page(institutionConsentEntityPages.getNumber())
                                .limit(institutionConsentEntityPages.getSize())
                                .totalElements(institutionConsentEntityPages.getTotalElements())
                                .totalPages((long) institutionConsentEntityPages.getTotalPages())
                                .build()
                ).build();
    }
}
