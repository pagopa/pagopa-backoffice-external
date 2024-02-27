package it.pagopa.selfcare.pagopa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerIbanEntity {

    private String ciFiscalCode;
    private String ciName;
    private String iban;
    private String label;
    private String status;
    private Instant activationDate;

}
