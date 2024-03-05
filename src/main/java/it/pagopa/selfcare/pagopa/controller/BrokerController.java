package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.ProblemJson;
import it.pagopa.selfcare.pagopa.service.BrokersService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Instance of a estController that defines the endpoints related to broker data retrieval
 */
@RestController
@Validated
@Tag(name = "Brokers APIs")
public class BrokerController {

    private final BrokersService brokersService;

    public BrokerController(BrokersService brokersService) {
        this.brokersService = brokersService;
    }


    /**
     * Retreive a paged list of broker related ibans
     * @param limit number of elements per page
     * @param page page to be selected
     * @return instance of BrokerIbansResponse, containing a paged list of ibans
     */
    @Operation(summary = "getAllIbans", description = "Return full merged Broker Iban List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/brokers/ibans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrokerIbansResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class)))
    })
    @Cacheable(value = "brokerIbans")
    public BrokerIbansResponse getBrokerIbans(
            @Parameter(description = "Number of elements on one page. Default = 10")
                    @RequestParam(required = false, defaultValue = "10") Integer limit,
            @Parameter(description = "Page number. Page value starts from 0") @RequestParam Integer page) {
        return brokersService.getBrokersIbans(limit, page);
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
        return brokersService.getBrokerInstitutions(brokerCode, limit, page);
    }

}
