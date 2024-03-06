package it.pagopa.selfcare.pagopa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CIIbansResource implements Serializable {

    @Schema(description = "Creditor Institution name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ciName;
    @Schema(description = "Creditor Institution tax code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ciFiscalCode;
    @Schema(description = "iban", requiredMode = Schema.RequiredMode.REQUIRED)
    private String iban;
    @Schema(description = "status", example = "ENABLED", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;
    @Schema(description = "Activation Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant validityDate;
    @Schema(description = "description")
    private String description;
    @Schema(description = "label", example = "CUP")
    private String label;

}
