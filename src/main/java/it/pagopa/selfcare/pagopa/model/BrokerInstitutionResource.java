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
    @Schema(description = "True if the EC has a broker", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean intermediated;
    @Schema(description = "Broker name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brokerCompanyName;
    @Schema(description = "Broker tax code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brokerTaxCode;
    @Schema(description = "Model of the station", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer model;
    @Schema(description = "Aux digit number (0,1,2,3, 0/3)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String auxDigit;
    @Schema(description = "Segregation code of the relation station-ec (unique per auxdigit)")
    private String segregationCode;
    @Schema(description = "Application code of the relation station-ec (unique per auxdigit)")
    private String applicationCode;
    @Schema(description = "CBILL code")
    private String cbillCode;
    @Schema(description = "Station code")
    private String stationId;
    @Schema(description = "Station state", example = "enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private String stationState;
    @Schema(description = "Activation date")
    private Instant activationDate;
    @Schema(description = "Station version", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;
    @Schema(description = "True if the station is for broadcast")
    private Boolean broadcast;
    @Schema(description = "True if allows payments by the psp")
    private Boolean pspPayment;
    @Schema(description = "Endpoint for Ricevuta Telematica", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String endpointRT;
    @Schema(description = "Endpoint for Redirect", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String endpointRedirect;
    @Schema(description = "Endpoint for Modello Unico", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String endpointMU;
    @Schema(description = "Version of the primitive", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer primitiveVersion;
    @Schema(description = "True if the CI is enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean ciStatus;
    @Schema(description = "True if ACA archive will be populated", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean aca;
    @Schema(description = "True if the debt positions can be paid in Stan In", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean standIn;

}
