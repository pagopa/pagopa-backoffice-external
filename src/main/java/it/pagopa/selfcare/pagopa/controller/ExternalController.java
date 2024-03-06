package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;
import it.pagopa.selfcare.pagopa.model.ProblemJson;
import it.pagopa.selfcare.pagopa.service.ExternalService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Instance of a estController that defines the endpoints related to broker data retrieval
 */
@RestController
@Validated
@Tag(name = "External APIs")
public class ExternalController {

    private final ExternalService externalService;

    @Autowired
    public ExternalController(ExternalService externalService) {
        this.externalService = externalService;
    }

    /**
     * Retrieve a paged list of broker related ibans
     * @param limit number of elements per page
     * @param page page to be selected
     * @return instance of BrokerIbansResponse, containing a paged list of ibans
     */
    @Operation(summary = "getAllIbans", description = "Return full merged Broker Iban List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/creditor_institutions/ibans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CIIbansResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class)))
    })
    @Cacheable(value = "getCIsIbans")
    public CIIbansResponse getCIsIbans(
            @Parameter(description = "Number of elements on one page. Default = 10")
                    @RequestParam(required = false, defaultValue = "10") Integer limit,
            @Parameter(description = "Page number. Page value starts from 0") @RequestParam Integer page) {
        return externalService.getBrokersIbans(limit, page);
    }

    /**
     * Retrieve a paged list of creditor institutions, using a specific broker code
     * @param brokerCode broker code to be used as filter
     * @param limit number of elements per page
     * @param page page to be used
     * @return paged list od broker related creditor institutions, filtered by code
     */
    @Operation(summary = "getBrokerInstitutions", description = "Return Broker Creditor Institution List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrokerInstitutionsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429",
                    description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500",
                    description = "Service unavailable", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemJson.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/brokers/{brokerCode}/creditor_institutions")
    @Cacheable(value = "brokerInstitutions")
    public BrokerInstitutionsResponse getCreditorInstitutions(
            @Parameter(description = "Broker Code to use as filter for the retrieved creditor institution list")
                @PathVariable("brokerCode") String brokerCode,
            @Parameter(description = "Number of elements on one page. Default = 10")
                @RequestParam(required = false, defaultValue = "10") Integer limit,
            @Parameter(description = "Page number. Page value starts from 0") @RequestParam Integer page) {
        return externalService.getBrokerInstitutions(brokerCode, limit, page);
    }

}
