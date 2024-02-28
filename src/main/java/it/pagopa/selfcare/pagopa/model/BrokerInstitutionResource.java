package it.pagopa.selfcare.pagopa.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerInstitutionResource implements Serializable {

    @ApiModelProperty(value = "nome dell'ente")
    private String companyName;
    @ApiModelProperty(value = "codice fiscale dell'ente")
    private String taxCode;
    @ApiModelProperty(value = "true se è intermediato, false se è diretto")
    private Boolean intermediated;
    @ApiModelProperty(value = "nome dell'intermediario")
    private String brokerCompanyName;
    @ApiModelProperty(value = "codice fiscale dell'intermediario")
    private String brokerTaxCode;
    @ApiModelProperty(value = "modello")
    private Integer model;
    @ApiModelProperty(value = "numero aux digit (0,1,2,3, 0/3)")
    private String auxDigit;
    @ApiModelProperty(value = "codice segregazione (univoco per ogni auxdigit)")
    private String segregationCode;
    @ApiModelProperty(value = "codice applicazione (univoco per ogni auxdigit)")
    private String applicationCode;
    @ApiModelProperty(value = "codice CBILL")
    private String cbillCode;
    @ApiModelProperty(value = "codice stazione")
    private String stationId;
    @ApiModelProperty(value = "stato stazione (abilitata/disabilitatta)")
    private String stationState;
    @ApiModelProperty(value = "data attivazione")
    private Instant activationDate;
    @ApiModelProperty(value = "versione stazione (1,2)")
    private String version;
    @ApiModelProperty(value = "true se è una stazione di broadcast")
    private Boolean broadcast;
}
