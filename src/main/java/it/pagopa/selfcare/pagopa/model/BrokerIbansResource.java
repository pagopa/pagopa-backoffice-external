package it.pagopa.selfcare.pagopa.model;

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

    private String denominazioneEnte;
    private String codiceFiscale;
    private String iban;
    private String stato;
    private Instant dataAttivazioneIban;
    private String descrizione;
    private String etichetta;

}
