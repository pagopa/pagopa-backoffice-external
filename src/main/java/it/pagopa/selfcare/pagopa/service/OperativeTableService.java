package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;

/**
 * Service containing the methods to be used for access operative table related data
 */
public interface OperativeTableService {

    /**
     * Retrieve the list of all operative tables, optionally the list can be filtered by business name and tax code
     *
     * @param taxCode the tax code
     * @param name the business name
     * @return  a list of operative tables
     */
    OperativeTableResourceList getOperativeTables(String taxCode, String name);
}
