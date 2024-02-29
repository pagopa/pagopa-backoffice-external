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

    @Schema(description = "Creditor Institution name")
    private String companyName;
    @Schema(description = "Creditor Institution tax code")
    private String taxCode;
    @Schema(description = "true if the EC has a broker")
    private Boolean intermediated;
    @Schema(description = "broker name")
    private String brokerCompanyName;
    @Schema(description = "broker tax code")
    private String brokerTaxCode;
    @Schema(description = "model of the station")
    private Integer model;
    @Schema(description = "aux digit number (0,1,2,3, 0/3)")
    private String auxDigit;
    @Schema(description = "segregation code of the relation station-ec (unique per auxdigit)")
    private String segregationCode;
    @Schema(description = "application code of the relation station-ec (unique per auxdigit)")
    private String applicationCode;
    @Schema(description = "CBILL code")
    private String cbillCode;
    @Schema(description = "station code")
    private String stationId;
    @Schema(description = "station state", example = "enabled")
    private String stationState;
    @Schema(description = "activation date")
    private Instant activationDate;
    @Schema(description = "station version", example = "1")
    private String version;
    @Schema(description = "true if the station is for broadcast")
    private Boolean broadcast;
}
