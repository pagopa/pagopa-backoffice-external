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
