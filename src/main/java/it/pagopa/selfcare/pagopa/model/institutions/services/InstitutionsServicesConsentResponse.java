package it.pagopa.selfcare.pagopa.model.institutions.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionsServicesConsentResponse {

    @Valid
    @JsonProperty("results")
    @NotNull
    @Size(min = 1)
    private List<InstitutionServiceConsent> results;

    @Valid
    @JsonProperty("hasNext")
    @NotNull
    @Schema(
            example = "true",
            description = "Boolean value indicating if there are more records left for input query"
    )
    private boolean hasNext;
}
