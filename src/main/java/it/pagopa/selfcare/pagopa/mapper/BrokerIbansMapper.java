package it.pagopa.selfcare.pagopa.mapper;

import it.pagopa.selfcare.pagopa.entities.BrokerIbanEntity;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that contains utility mapping methods to convert a BrokerIbanEntity into a BrokerIbansResource
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerIbansMapper {

    public static BrokerIbansResource toResource(BrokerIbanEntity broker) {
        if(broker == null) {
            return null;
        }
        return BrokerIbansResource.builder()
                .iban(broker.getIban())
                .dataAttivazioneIban(broker.getValidityDate())
                .stato(broker.getStatus())
                .codiceFiscale(broker.getCiFiscalCode())
                .denominazioneEnte(broker.getCiName())
                .descrizione(broker.getDescription())
                .etichetta(broker.getLabel())
                .build();
    }

    public static List<BrokerIbansResource> toResources(List<BrokerIbanEntity> brokerIbanEntities) {
        return brokerIbanEntities == null ? Collections.emptyList() :
                brokerIbanEntities.stream()
                .map(BrokerIbansMapper::toResource)
                .collect(Collectors.toList());
    }

}
