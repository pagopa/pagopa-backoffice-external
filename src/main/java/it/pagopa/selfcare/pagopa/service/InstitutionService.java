package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.institutions.services.InstitutionsServiceFilter;
import it.pagopa.selfcare.pagopa.model.institutions.services.InstitutionsServicesConsentResponse;


/**
 * Service containing the methods to be used for access institution services consent data
 */
public interface InstitutionService {

    /**
     * Retrive a paged list of institution consent filtered by consent type and by starting and ending date
     */
    InstitutionsServicesConsentResponse getInstitutionServiceConsentFilteredByDatesAndByConsent(InstitutionsServiceFilter institutionsServiceFilter);
}
