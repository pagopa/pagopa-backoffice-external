package it.pagopa.selfcare.pagopa.model.institutions.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.pagopa.selfcare.pagopa.model.PageInfo;
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
    @JsonProperty("pageInfo")
    @NotNull
    private PageInfo pageInfo;
}
