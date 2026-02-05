package it.pagopa.selfcare.pagopa.entities;


import it.pagopa.selfcare.pagopa.model.institutions.services.Consent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("institutions-services-rtp-consent")
@ToString
public class InstitutionConsentEntity {

    @Id
    private String id;

    @Indexed(unique = false)
    private Consent consent;

    @Indexed(unique = false)
    private OffsetDateTime consentDate;

    private String institutionTaxCode;

    private String name;


}
