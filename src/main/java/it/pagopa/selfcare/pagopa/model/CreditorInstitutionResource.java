package it.pagopa.selfcare.pagopa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditorInstitutionResource implements Serializable {

    @Schema(description = "Creditor Institution name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;
    @Schema(description = "Creditor Institution tax code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String taxCode;
}
