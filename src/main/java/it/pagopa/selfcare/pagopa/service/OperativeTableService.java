package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;

/**
 * Service containing the methods to be used for access operative table related data
 */
public interface OperativeTableService {

    /**
     * Retrieve the list of all operative tables
     *
     * @return a list of operative tables
     */
    OperativeTableResourceList getOperativeTables();
}
