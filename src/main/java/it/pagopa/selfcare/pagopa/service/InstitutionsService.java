package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.GetEcResponse;

public interface InstitutionsService {
    GetEcResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page);

}
