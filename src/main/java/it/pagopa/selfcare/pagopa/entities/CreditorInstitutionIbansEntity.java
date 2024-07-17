package it.pagopa.selfcare.pagopa.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document("creditorInstitutionIbans")
@Getter
@Setter
@ToString
public class CreditorInstitutionIbansEntity extends IbanEntity {

    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

}
