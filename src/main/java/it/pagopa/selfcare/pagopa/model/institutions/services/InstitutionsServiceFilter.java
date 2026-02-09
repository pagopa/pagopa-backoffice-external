package it.pagopa.selfcare.pagopa.model.institutions.services;


import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionsServiceFilter {

    private ServiceId serviceId;
    private int page;
    private int pageSize;
    private Consent consent;
    private OffsetDateTime startingData;
    private OffsetDateTime endingDate;




}
