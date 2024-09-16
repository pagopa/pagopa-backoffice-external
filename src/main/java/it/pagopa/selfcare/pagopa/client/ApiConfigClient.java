package it.pagopa.selfcare.pagopa.client;

import it.pagopa.selfcare.pagopa.config.feign.ApiConfigFeignConfig;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.CreateStationMaintenance;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.MaintenanceHoursSummaryResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.UpdateStationMaintenance;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;

@FeignClient(name = "api-config", url = "${rest-client.api-config.base-url}", configuration = ApiConfigFeignConfig.class)
public interface ApiConfigClient {

    @PostMapping(value = "brokers/{broker-code}/station-maintenances", produces = {MediaType.APPLICATION_JSON_VALUE})
    StationMaintenanceResource createStationMaintenance(
            @PathVariable("broker-code") String brokerCode,
            @RequestBody @Valid @NotNull CreateStationMaintenance createStationMaintenance
    );

    @PutMapping(value = "brokers/{broker-code}/station-maintenances/{maintenance-id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    StationMaintenanceResource updateStationMaintenance(
            @PathVariable("broker-code") String brokerCode,
            @PathVariable("maintenance-id") Long maintenanceId,
            @RequestBody @Valid @NotNull UpdateStationMaintenance updateStationMaintenance
    );

    @GetMapping(value = "brokers/{broker-code}/station-maintenances", produces = {MediaType.APPLICATION_JSON_VALUE})
    StationMaintenanceListResource getStationMaintenances(
            @PathVariable("broker-code") String brokerCode,
            @RequestParam(required = false) String stationCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDateTimeBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDateTimeAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDateTimeBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDateTimeAfter,
            @RequestParam(required = false, defaultValue = "50") @Positive Integer limit,
            @RequestParam(required = false, defaultValue = "0") @Min(0) @PositiveOrZero Integer page
    );

    @GetMapping(value = "brokers/{broker-code}/station-maintenances/summary", produces = {MediaType.APPLICATION_JSON_VALUE})
    MaintenanceHoursSummaryResource getBrokerMaintenancesSummary(
            @PathVariable("broker-code") String brokerCode,
            @RequestParam @Size(min = 4, max = 4) String maintenanceYear
    );

    @GetMapping(value = "brokers/{broker-code}/station-maintenances/{maintenance-id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    StationMaintenanceResource getStationMaintenance(
            @PathVariable("broker-code") String brokerCode,
            @PathVariable("maintenance-id") Long maintenanceId
    );

    @DeleteMapping(value = "brokers/{broker-code}/station-maintenances/{maintenance-id}")
    void deleteStationMaintenance(
            @PathVariable("broker-code") String brokerCode,
            @PathVariable("maintenance-id") Long maintenanceId
    );
}
