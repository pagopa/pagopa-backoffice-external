package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.client.ApiConfigClient;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.MaintenanceHoursSummaryResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListState;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StationMaintenanceServiceImpl.class})
class StationMaintenanceServiceImplTest {

    private static final String STATION_CODE = "stationCode";
    private static final String BROKER_CODE = "brokerCode";
    private static final long MAINTENANCE_ID = 100;
    private static final int YEAR_FILTER = 2024;

    @Autowired
    private StationMaintenanceServiceImpl stationMaintenanceService;
    @MockBean
    private ApiConfigClient apiConfigClient;

    @Test
    void getStationMaintenancesFINISHEDWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.FINISHED, null, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), eq(null), eq(null), any(OffsetDateTime.class), eq(null), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesFINISHEDWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.FINISHED, YEAR_FILTER, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesIN_PROGRESSWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.IN_PROGRESS, null, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), any(OffsetDateTime.class), eq(null), eq(null), any(OffsetDateTime.class), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesIN_PROGRESSWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.IN_PROGRESS, YEAR_FILTER, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), any(OffsetDateTime.class), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesSCHEDULEDWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.SCHEDULED, null, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), eq(null), any(OffsetDateTime.class), eq(null), eq(null), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesSCHEDULEDWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.SCHEDULED, YEAR_FILTER, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), eq(null), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesSCHEDULED_AND_IN_PROGRESSWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS, null, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), eq(null), eq(null), eq(null), any(OffsetDateTime.class), anyInt(), anyInt());
    }

    @Test
    void getStationMaintenancesSCHEDULED_AND_IN_PROGRESSWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getStationMaintenances(anyString(), anyString(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                BROKER_CODE, STATION_CODE, StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS, YEAR_FILTER, 0, 0)
        );

        assertNotNull(result);

        verify(apiConfigClient).getStationMaintenances(anyString(), anyString(), any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), any(OffsetDateTime.class), anyInt(), anyInt());
    }

    @Test
    void getAllStationsMaintenancesFINISHEDWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.FINISHED, null, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(eq(null), eq(null), any(OffsetDateTime.class), eq(null));
    }

    @Test
    void getAllStationsMaintenancesFINISHEDWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.FINISHED, YEAR_FILTER, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(any(OffsetDateTime.class), any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null));
    }

    @Test
    void getAllStationsMaintenancesIN_PROGRESSWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.IN_PROGRESS, null, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(any(OffsetDateTime.class), eq(null), eq(null), any(OffsetDateTime.class));
    }

    @Test
    void getAllStationsMaintenancesIN_PROGRESSWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.IN_PROGRESS, YEAR_FILTER, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), any(OffsetDateTime.class));
    }

    @Test
    void getAllStationsMaintenancesSCHEDULEDWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.SCHEDULED, null, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(eq(null), any(OffsetDateTime.class), eq(null), eq(null));
    }

    @Test
    void getAllStationsMaintenancesSCHEDULEDWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.SCHEDULED, YEAR_FILTER, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), eq(null));
    }

    @Test
    void getAllStationsMaintenancesSCHEDULED_AND_IN_PROGRESSWithoutYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS, null, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(eq(null), eq(null), eq(null), any(OffsetDateTime.class));
    }


    @Test
    void getAllStationsMaintenancesSCHEDULED_AND_IN_PROGRESSWithYearFilterSuccess() {
        StationMaintenanceResource maintenanceResource = buildMaintenanceResource();
        StationMaintenanceListResource response = new StationMaintenanceListResource();
        response.setMaintenanceList(Collections.singletonList(maintenanceResource));
        response.setPageInfo(new PageInfo());

        when(apiConfigClient.getAllStationsMaintenances(any(), any(), any(), any())).thenReturn(response);

        StationMaintenanceListResource result = assertDoesNotThrow(() -> stationMaintenanceService.getStationMaintenances(
                eq(null), eq(null), StationMaintenanceListState.SCHEDULED_AND_IN_PROGRESS, YEAR_FILTER, eq(null), eq(null))
        );

        assertNotNull(result);

        verify(apiConfigClient).getAllStationsMaintenances(any(OffsetDateTime.class), any(OffsetDateTime.class), eq(null), any(OffsetDateTime.class));
    }

    @Test
    void getBrokerMaintenancesSummarySuccess() {
        MaintenanceHoursSummaryResource mockedResult = MaintenanceHoursSummaryResource.builder()
                .usedHours("2")
                .scheduledHours("3")
                .remainingHours("31")
                .extraHours("0")
                .annualHoursLimit("36")
                .build();
        when(apiConfigClient.getBrokerMaintenancesSummary(any(), any())).thenReturn(mockedResult);
        MaintenanceHoursSummaryResource result =
                stationMaintenanceService.getBrokerMaintenancesSummary(BROKER_CODE, "2024");
        assertNotNull(result);
        assertEquals(mockedResult, result);
    }

    @Test
    void getStationMaintenanceDetailSuccess() {
        StationMaintenanceResource mockedResult = buildMaintenanceResource();
        when(apiConfigClient.getStationMaintenance(any(), any())).thenReturn(mockedResult);
        StationMaintenanceResource resource =
                stationMaintenanceService.getStationMaintenance("brokerCode", 1L);
        assertNotNull(resource);
        assertEquals(mockedResult, resource);
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
