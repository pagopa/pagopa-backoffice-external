package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.ProblemJson;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.MaintenanceHoursSummaryResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListResource;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceListState;
import it.pagopa.selfcare.pagopa.model.stationmaintenance.StationMaintenanceResource;
import it.pagopa.selfcare.pagopa.service.StationMaintenanceService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/station-maintenances", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Station Maintenance")
public class StationMaintenanceController {

    private final StationMaintenanceService stationMaintenanceService;

    @Autowired
    public StationMaintenanceController(StationMaintenanceService stationMaintenanceService) {
        this.stationMaintenanceService = stationMaintenanceService;
    }

    @Operation(summary = "Get list of all stations' maintenance filtered by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StationMaintenanceListResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, external = true, internal = false)
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public StationMaintenanceListResource getAllStationsMaintenances(
            @Parameter(description = "Maintenances' state") @RequestParam(required = false) StationMaintenanceListState state,
            @Parameter(description = "Maintenance's starting year") @RequestParam(required = false) Integer year
    ) {
        return this.stationMaintenanceService.getStationMaintenances(
                null,
                null,
                state,
                year,
                null,
                null
        );
    }

    @Operation(summary = "Get a paginated list of station's maintenance for the specified broker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StationMaintenanceListResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, external = true, internal = false)
    @GetMapping(value = "/{broker-tax-code}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StationMaintenanceListResource getStationMaintenances(
            @Parameter(description = "Broker's tax code") @PathVariable("broker-tax-code") String brokerCode,
            @Parameter(description = "Station's code") @RequestParam(name = "station-code", required = false) String stationCode,
            @Parameter(description = "Maintenances' state") @RequestParam(required = false) StationMaintenanceListState state,
            @Parameter(description = "Maintenance's starting year") @RequestParam(required = false) Integer year,
            @Parameter(description = "Number of items for page") @RequestParam(required = false, defaultValue = "50") @Positive Integer limit,
            @Parameter(description = "Page number") @RequestParam(required = false, defaultValue = "0") @Min(0) @PositiveOrZero Integer page
    ) {
        return this.stationMaintenanceService.getStationMaintenances(
                brokerCode,
                stationCode,
                state,
                year,
                limit,
                page
        );
    }

    /**
     * Retrieves broker related station maintenance summary for the provided year
     *
     * @param brokerCode      broker id to use for summary retrieval
     * @param maintenanceYear year in format yyyy, to be used for summary retrieval
     * @return maintenance summary for the provided year and brokerCode
     */
    @Operation(summary = "Get the hours' summary of stations' maintenance for the specified broker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MaintenanceHoursSummaryResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, external = true, internal = false)
    @GetMapping(value = "/{broker-tax-code}/summary", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MaintenanceHoursSummaryResource getBrokerMaintenancesSummary(
            @Parameter(description = "Broker's tax code") @PathVariable("broker-tax-code") String brokerCode,
            @Parameter(description = "Year of maintenance (yyyy)", example = "2024") @RequestParam(name = "maintenance-year") @Size(min = 4, max = 4) String maintenanceYear
    ) {
        return this.stationMaintenanceService.getBrokerMaintenancesSummary(brokerCode, maintenanceYear);
    }

    /**
     * Recovers a station maintenance, given its brokerCode and maintenanceId.
     * If the provided brokerCode doesn't match the one related to the persisted one for the given maintenance,
     * it will throw the maintenance not found exception
     *
     * @param brokerCode    brokerCode to be used as filter in the maintenance recovery
     * @param maintenanceId station maintenance id to be used for the detail recovery
     * @return station maintenance data, provided in an instance of StationMaintenanceResource
     * @throws AppException thrown when a maintenance, given the input data, has not been found
     */
    @Operation(summary = "Get a maintenance for the specified station, given its broker code and maintenance id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StationMaintenanceResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.WRITE, external = true, internal = false)
    @GetMapping(value = "/{broker-tax-code}/maintenance/{maintenance-id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public StationMaintenanceResource getStationMaintenance(
            @Parameter(description = "Broker's tax code") @PathVariable("broker-tax-code") String brokerCode,
            @Parameter(description = "Maintenance's id") @PathVariable("maintenance-id") Long maintenanceId
    ) {
        return this.stationMaintenanceService.getStationMaintenance(brokerCode, maintenanceId);
    }
}
