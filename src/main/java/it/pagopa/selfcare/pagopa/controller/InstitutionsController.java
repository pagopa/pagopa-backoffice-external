package it.pagopa.selfcare.pagopa.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.model.ProblemJson;
import it.pagopa.selfcare.pagopa.model.institutions.services.*;
import it.pagopa.selfcare.pagopa.service.InstitutionService;
import it.pagopa.selfcare.pagopa.util.Constants;
import it.pagopa.selfcare.pagopa.util.OffsetDateTimeDeserializer;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/institutions")
@Tag(name = "Institution services")
public class InstitutionsController {

    @Autowired
    InstitutionService institutionService;

    @GetMapping(value = "/services/{serviceId}/consents", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "getInstitutionsServiceConsent", description = "Get service consent for all institutions", security = {@SecurityRequirement(name = "ApiKey")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InstitutionsServicesConsentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemJson.class)))
    })
    @OpenApiTableMetadata(cacheable = true, internal = false, external = true)
    public InstitutionsServicesConsentResponse getInstitutionsServiceConsent(
            @Parameter(description = "Service unique identifier", required = true)
            @PathVariable(value = "serviceId")
            @NotNull
            ServiceId serviceId,
            @Parameter(description = "Query pagination - searched page number", required = true)
            @RequestParam(name = "pageNumber")
            @Min(0)
            @NotNull
            Integer pageNumber,
            @Parameter(description = "Query pagination - page size", required = true)
            @RequestParam(name = "pageSize")
            @NotNull
            @Min(0) @Max(1000)
            Integer pageSize,
            @Parameter(description = "Filter parameter - Filter consents for a specific consent type")
            @RequestParam(name = "consent", required = false)
            Consent consent,
            @Parameter(description = "Filter parameter - Filter consents starting from date (inclusive). No data filter applied if null")
            @RequestParam(name = "fromDate", required = false)
            @JsonFormat(pattern = Constants.ZONED_DATE_TIME_FORMAT)
            @JsonSerialize(using = OffsetDateTimeSerializer.class)
            @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
            OffsetDateTime fromDate,
            @Parameter(description = "Filter parameter - Filter consents up to date (inclusive). No data filter applied if null")
            @RequestParam(name = "toDate", required = false)
            @JsonFormat(pattern = Constants.ZONED_DATE_TIME_FORMAT)
            @JsonSerialize(using = OffsetDateTimeSerializer.class)
            @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
            OffsetDateTime toDate

    ) {
        // Call the service for retrive the list of institutions filtered by parameters
        if(fromDate != null && toDate != null)
            return institutionService.getIstitutionServiceConsentFilteredByDates(pageNumber, pageSize, fromDate, toDate);
        else
            return institutionService.getIstitutionServiceConsent(pageNumber, pageSize);

    }
}
