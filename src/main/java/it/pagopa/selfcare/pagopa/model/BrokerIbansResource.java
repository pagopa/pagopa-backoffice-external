package it.pagopa.selfcare.pagopa.model;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "nome dell'ente")
    private String ciName;
    @ApiModelProperty(value = "codice fiscale dell'ente")
    private String ciFiscalCode;
    @ApiModelProperty(value = "iban")
    private String iban;
    @ApiModelProperty(value = "stato")
    private String status;
    @ApiModelProperty(value = "data di attivazione")
    private Instant validityDate;
    @ApiModelProperty(value = "descrizione")
    private String description;
    @ApiModelProperty(value = "etichetta")
    private String label;

}
