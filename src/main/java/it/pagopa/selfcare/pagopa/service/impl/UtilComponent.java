package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.repository.CreditorInstitutionIbansRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UtilComponent {

    private final CreditorInstitutionIbansRepository creditorInstitutionIbansRepository;

    public UtilComponent(CreditorInstitutionIbansRepository creditorInstitutionIbansRepository) {
        this.creditorInstitutionIbansRepository = creditorInstitutionIbansRepository;
    }

    @Cacheable("getTotalDocuments")
    public long getTotalDocuments() {
        return creditorInstitutionIbansRepository.count();
    }
}
