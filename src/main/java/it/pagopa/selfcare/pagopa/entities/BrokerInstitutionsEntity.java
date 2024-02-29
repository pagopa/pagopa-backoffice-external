package it.pagopa.selfcare.pagopa.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "brokerCode")
@Document("brokerInstitutions")
@ToString
public class BrokerInstitutionsEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String brokerCode;

    @CreatedDate
    private Instant createdAt;

    private List<BrokerInstitutionEntity> institutions;

}
