package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;

public interface BrokersService {
    BrokerInstitutionsResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page);

    BrokerIbansResponse getBrokersIbans(Integer limit, Integer page);
}
