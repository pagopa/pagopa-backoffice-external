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
public class BrokerIbansResource implements Serializable {

    @Schema(description = "Creditor Institution name")
    private String ciName;
    @Schema(description = "Creditor Institution tax code")
    private String ciFiscalCode;
    @Schema(description = "iban")
    private String iban;
    @Schema(description = "status", example = "ENABLED")
    private String status;
    @Schema(description = "Activation Date")
    private Instant validityDate;
    @Schema(description = "description")
    private String description;
    @Schema(description = "label", example = "CUP")
    private String label;

}
