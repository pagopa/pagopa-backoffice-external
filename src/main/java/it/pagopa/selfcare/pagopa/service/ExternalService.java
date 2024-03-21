package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;

/**
 * Service containing the methods to be used for access broker related data
 */
public interface ExternalService {

    /**
     * Retrieve a paged list of creditor institutions, using a specific broker code
     * @param brokerCode broker code to be used as filter
     * @param limit number of elements per page
     * @param page page to be used
     * @return paged list od broker related creditor institutions, filtered by code
     */
    BrokerInstitutionsResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page);

    /**
     * Retrieve a paged list of ibans related to the brokers
     * @param limit number of elements per page
     * @param page page to be used
     * @return paged list od broker related ibans
     */
    CIIbansResponse getBrokersIbans(Integer limit, Integer page);

    /**
     * Retrieve a paged list of ibans, using a specific broker code
     *
     * @param brokerCode broker code to be used as filter
     * @param limit      number of elements per page
     * @param page       page to be used
     * @return paged list od broker related ibans, filtered by code
     */
    CIIbansResponse getBrokerIbans(String brokerCode, Integer limit, Integer page);
}
