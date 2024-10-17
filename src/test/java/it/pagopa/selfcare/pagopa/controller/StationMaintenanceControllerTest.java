package it.pagopa.selfcare.pagopa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.*;
import it.pagopa.selfcare.pagopa.service.StationMaintenanceService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StationMaintenanceControllerTest {

    private static final String STATION_CODE = "stationCode";
    private static final String BROKER_CODE = "brokerCode";
    private static final long MAINTENANCE_ID = 100;

    @MockBean
    private StationMaintenanceService stationMaintenanceService;
    @Autowired
    private MockMvc mvc;
    @Inject
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(stationMaintenanceService);
    }

    @Test
    void getStationMaintenances() throws Exception {
        StationMaintenanceResource maintenanceResource = new StationMaintenanceResource();
        maintenanceResource.setStationCode(STATION_CODE);
        maintenanceResource.setStandIn(true);
        maintenanceResource.setEndDateTime(OffsetDateTime.now());
        maintenanceResource.setStartDateTime(OffsetDateTime.now());
        maintenanceResource.setMaintenanceId(MAINTENANCE_ID);
        maintenanceResource.setBrokerCode(BROKER_CODE);
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());
        when(stationMaintenanceService.getStationMaintenances(anyString(), anyString(), any(StationMaintenanceListState.class), anyInt(), anyInt(), anyInt())).thenReturn(response);

        mvc.perform(get("/station-maintenances/{broker-tax-code}", BROKER_CODE)
                        .param("station-code", STATION_CODE)
                        .param("state", String.valueOf(StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS))
                        .param("year", String.valueOf(2024))
                        .param("limit", String.valueOf(10))
                        .param("page", String.valueOf(0))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStationsMaintenances() throws Exception {
        StationMaintenanceResource maintenanceResource = new StationMaintenanceResource();
        maintenanceResource.setStationCode(STATION_CODE);
        maintenanceResource.setStandIn(true);
        maintenanceResource.setEndDateTime(OffsetDateTime.now());
        maintenanceResource.setStartDateTime(OffsetDateTime.now());
        maintenanceResource.setMaintenanceId(MAINTENANCE_ID);
        maintenanceResource.setBrokerCode(BROKER_CODE);
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());
        when(stationMaintenanceService.getAllStationsMaintenances(any(StationMaintenanceListState.class), anyInt())).thenReturn(response);

        mvc.perform(get("/station-maintenances")
                        .param("state", String.valueOf(StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS))
                        .param("year", String.valueOf(2024))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBrokerMaintenancesSummaryTest() throws Exception {
        when(stationMaintenanceService.getBrokerMaintenancesSummary(anyString(), anyString()))
                .thenReturn(MaintenanceHoursSummaryResource.builder()
                        .usedHours("2")
                        .scheduledHours("3")
                        .remainingHours("31")
                        .extraHours("0")
                        .annualHoursLimit("36")
                        .build());

        mvc.perform(get("/station-maintenances/{broker-tax-code}/summary", BROKER_CODE)
                        .param("maintenance-year", "2024")
                ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getStationMaintenanceTest() throws Exception {
        when(stationMaintenanceService.getStationMaintenance(anyString(), anyLong()))
                .thenReturn(buildMaintenanceResource());

        mvc.perform(get("/station-maintenances/{broker-tax-code}/maintenance/{maintenance-id}", BROKER_CODE, MAINTENANCE_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getStationMaintenanceTestOnKO() throws Exception {
        when(stationMaintenanceService.getStationMaintenance(anyString(), anyLong()))
                .thenThrow(new AppException(AppError.INTERNAL_SERVER_ERROR));

        mvc.perform(get("/station-maintenances/{broker-tax-code}/maintenance/{maintenance-id}", BROKER_CODE, MAINTENANCE_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private StationMaintenanceResource buildMaintenanceResource() {
        StationMaintenanceResource resource = new StationMaintenanceResource();
        resource.setStationCode(STATION_CODE);
        resource.setStandIn(true);
        resource.setEndDateTime(OffsetDateTime.now());
        resource.setStartDateTime(OffsetDateTime.now());
        resource.setMaintenanceId(MAINTENANCE_ID);
        resource.setBrokerCode(BROKER_CODE);
        return resource;
    }
}
