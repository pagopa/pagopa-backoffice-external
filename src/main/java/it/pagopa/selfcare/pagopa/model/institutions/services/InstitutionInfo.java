package it.pagopa.selfcare.pagopa.model.institutions.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionInfo {
    @JsonProperty("taxCode")
    @NotNull
    @Schema(
            example = "77777777777",
            description = "Institution tax code")
    private String taxCode;
    
    @JsonProperty("name")
    @NotNull
    @Schema(
            example = "EC name",
            description = "Institution name")
    private String name;
}
