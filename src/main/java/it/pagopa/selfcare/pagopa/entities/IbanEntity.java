package it.pagopa.selfcare.pagopa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IbanEntity {

    private String ciFiscalCode;
    private String ciName;
    private String iban;
    private String label;
    private String status;
    private String description;
    private Instant validityDate;

}
