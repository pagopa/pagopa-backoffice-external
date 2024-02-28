package it.pagopa.selfcare.pagopa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerInstitutionResource implements Serializable {
    private String companyName;
    private String taxCode;
    private Boolean intermediated;
    private String brokerCompanyName;
    private String brokerTaxCode;
    private Integer model;
    private String auxDigit;
    private String segregationCode;
    private String applicationCode;
    private String cbillCode;
    private String stationId;
    private String stationState;
    private Instant activationDate;
    private String version;
    private Boolean broadcast;
}
