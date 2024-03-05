package it.pagopa.selfcare.pagopa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerInstitutionResource implements Serializable {

    @Schema(description = "Creditor Institution name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;
    @Schema(description = "Creditor Institution tax code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String taxCode;
    @Schema(description = "true if the EC has a broker", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean intermediated;
    @Schema(description = "broker name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brokerCompanyName;
    @Schema(description = "broker tax code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brokerTaxCode;
    @Schema(description = "model of the station", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer model;
    @Schema(description = "aux digit number (0,1,2,3, 0/3)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String auxDigit;
    @Schema(description = "segregation code of the relation station-ec (unique per auxdigit)")
    private String segregationCode;
    @Schema(description = "application code of the relation station-ec (unique per auxdigit)")
    private String applicationCode;
    @Schema(description = "CBILL code")
    private String cbillCode;
    @Schema(description = "station code")
    private String stationId;
    @Schema(description = "station state", example = "enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private String stationState;
    @Schema(description = "activation date")
    private Instant activationDate;
    @Schema(description = "station version", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;
    @Schema(description = "true if the station is for broadcast")
    private Boolean broadcast;
    @Schema(description = "true if allows payments by the psp")
    private Boolean pspPayment;

}
