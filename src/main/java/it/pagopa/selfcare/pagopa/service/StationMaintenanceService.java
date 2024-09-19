package it.pagopa.selfcare.pagopa.service;

import it.pagopa.selfcare.pagopa.model.stationmaintenance.*;

public interface StationMaintenanceService {

    /**
     * Retrieves the list of station's maintenance of the specified broker that match the provided filters
     *
     * @param brokerCode  broker's tax code
     * @param stationCode station's code, used to filter out results
     * @param state       state of the maintenance (based on start and end date), used to filter out results
     * @param year        year of the maintenance, used to filter out results
     * @param limit       size of the requested page
     * @param page        page number
     * @return the filtered list of station's maintenance
     */
    StationMaintenanceListResource getStationMaintenances(
            String brokerCode,
            String stationCode,
            StationMaintenanceListState state,
            Integer year,
            Integer limit,
            Integer page
    );

    /**
     * Retrieves broker related station maintenance summary for the provided year
     *
     * @param brokerCode      broker id to use for summary retrieval
     * @param maintenanceYear year in format yyyy, to be used for summary retrieval
     * @return maintenance summary for the provided year and brokerCode
     */
    MaintenanceHoursSummaryResource getBrokerMaintenancesSummary(String brokerCode, String maintenanceYear);

    /**
     * Recovers a station maintenance, given its brokerCode and maintenanceId.
     * If the provided brokerCode doesn't match the one related to the persisted one for the given maintenance,
     * it will throw the maintenance not found exception
     *
     * @param brokerCode    brokerCode to be used as filter in the maintenance recovery
     * @param maintenanceId station maintenance id to be used for the detail recovery
     * @return station maintenance data, provided in an instance of StationMaintenanceResource
     */
    StationMaintenanceResource getStationMaintenance(String brokerCode, Long maintenanceId);
}
