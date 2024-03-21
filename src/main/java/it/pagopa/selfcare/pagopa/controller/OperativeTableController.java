package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.model.ProblemJson;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;
import it.pagopa.selfcare.pagopa.service.OperativeTableService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/operative_tables")
@Tag(name = "Operative Tables")
public class OperativeTableController {

    private final OperativeTableService operativeTableService;

    @Autowired
    public OperativeTableController(OperativeTableService operativeTableService) {
        this.operativeTableService = operativeTableService;
    }

    /**
     * Retrieve a list of all operative table, optionally the list can be filtered by business name and tax code
     *
     * @param taxCode the tax code
     * @param businessName the business name
     * @return a list of operative tables
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "getOperativeTables", description = "Get All operative tables", security = {@SecurityRequirement(name = "ApiKey")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OperativeTableResourceList.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(cacheable = true, internal = false, external = true)
    public OperativeTableResourceList getOperativeTables(
            @Parameter(description = "Tax code") @RequestParam(required = false, defaultValue = "") String taxCode,
            @Parameter(description = "Business name") @RequestParam(required = false, defaultValue = "") String businessName
    ) {
        return this.operativeTableService.getOperativeTables(taxCode, businessName);
    }
}
