package it.pagopa.selfcare.pagopa.model.institutions.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionServiceConsent {

    @Valid
    @JsonProperty("institutionInfo")
    @NotNull
    private InstitutionInfo institutionInfo;

    @Valid
    @JsonProperty("consentInfo")
    @NotNull
    private ConsentInfo consentInfo;

}
