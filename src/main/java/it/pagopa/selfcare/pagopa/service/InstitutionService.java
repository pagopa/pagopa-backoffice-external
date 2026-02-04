package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.institutions.services.InstitutionsServicesConsentResponse;

import java.time.OffsetDateTime;

/**
 * Service containing the methods to be used for access institution services consent data
 */
public interface InstitutionService {

    /**
     * Retrive a paged list of institution consent
     */
    InstitutionsServicesConsentResponse getIstitutionServiceConsent(int page, int size);

    /**
     * Retrive a paged list of institution consent filtered by starting and ending date
     */
    InstitutionsServicesConsentResponse getIstitutionServiceConsentFilteredByDates(int page, int size, OffsetDateTime startingDate, OffsetDateTime endingDate);


}
