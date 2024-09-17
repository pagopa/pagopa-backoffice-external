package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.client.ApiConfigClient;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.CreateStationMaintenance;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.MaintenanceHoursSummaryResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListState;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.UpdateStationMaintenance;
import it.pagopa.selfcare.pagopa.service.StationMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class StationMaintenanceServiceImpl implements StationMaintenanceService {

    private final ApiConfigClient apiConfigClient;

    @Autowired
    public StationMaintenanceServiceImpl(ApiConfigClient apiConfigClient) {
        this.apiConfigClient = apiConfigClient;
    }

    /**
     * @inheritDoc
     */
    @Override
    public StationMaintenanceListResource getStationMaintenances(
            String brokerCode,
            String stationCode,
            StationMaintenanceListState state,
            Integer year,
            Integer limit,
            Integer page
    ) {
        OffsetDateTime startDateTimeBefore = null;
        OffsetDateTime startDateTimeAfter = null;
        OffsetDateTime endDateTimeBefore = null;
        OffsetDateTime endDateTimeAfter = null;

        if (state != null) {
            if (state.equals(StationMaintenanceListState.FINISHED)) {
                endDateTimeBefore = getDateToday();
            }
            if (state.equals(StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS)) {
                endDateTimeAfter = getDateToday();
            }
            if (state.equals(StationMaintenanceListState.SCHEDULED)) {
                startDateTimeAfter = getDateToday();
            }
            if (state.equals(StationMaintenanceListState.IN_PROGRESS)) {
                startDateTimeBefore = getDateToday();
                endDateTimeAfter = getDateToday();
            }
        }

        if (year != null
        ) {
            startDateTimeBefore = startDateTimeBefore != null ? startDateTimeBefore.withYear(year) : getEndOfYear(year);
            startDateTimeAfter = startDateTimeAfter != null ? startDateTimeAfter.withYear(year) : getStartOfYear(year);
        }

        return this.apiConfigClient.getStationMaintenances(
                brokerCode,
                stationCode,
                startDateTimeBefore,
                startDateTimeAfter,
                endDateTimeBefore,
                endDateTimeAfter,
                limit,
                page
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    public StationMaintenanceResource createStationMaintenance(
            String brokerCode,
            CreateStationMaintenance createStationMaintenance
    ) {
        return this.apiConfigClient.createStationMaintenance(brokerCode, createStationMaintenance);
    }

    /**
     * @inheritDoc
     */
    public StationMaintenanceResource updateStationMaintenance(
            String brokerCode,
            Long maintenanceId,
            UpdateStationMaintenance updateStationMaintenance
    ) {
        return this.apiConfigClient.updateStationMaintenance(brokerCode, maintenanceId, updateStationMaintenance);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MaintenanceHoursSummaryResource getBrokerMaintenancesSummary(String brokerCode, String maintenanceYear) {
        return this.apiConfigClient.getBrokerMaintenancesSummary(brokerCode, maintenanceYear);
    }

    /**
     * @inheritDoc
     */
    @Override
    public StationMaintenanceResource getStationMaintenance(String brokerCode, Long maintenanceId) {
        return this.apiConfigClient.getStationMaintenance(brokerCode, maintenanceId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteStationMaintenance(
            String brokerCode,
            Long maintenanceId
    ) {
        this.apiConfigClient.deleteStationMaintenance(brokerCode, maintenanceId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void finishStationMaintenance(String brokerCode, Long maintenanceId) {
        StationMaintenanceResource maintenance = this.apiConfigClient.getStationMaintenance(brokerCode, maintenanceId);

        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (!(maintenance.getStartDateTime().isBefore(now) && maintenance.getEndDateTime().isAfter(now))) {
            throw new AppException(AppError.STATION_MAINTENANCE_NOT_IN_PROGRESS);
        }

        long mod = now.getMinute() % 15;
        UpdateStationMaintenance update = UpdateStationMaintenance.builder()
                .endDateTime(now.plusMinutes(15 - mod))
                .build();

        this.apiConfigClient.updateStationMaintenance(brokerCode, maintenanceId, update);
    }

    private OffsetDateTime getDateToday() {
        return OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    private OffsetDateTime getStartOfYear(int year) {
        return getDateToday().withYear(year).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0);
    }

    private OffsetDateTime getEndOfYear(int year) {
        return getDateToday().withYear(year).withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59);
    }
}
