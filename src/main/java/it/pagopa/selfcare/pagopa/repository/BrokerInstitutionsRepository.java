package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionAggregate;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrokerInstitutionsRepository extends MongoRepository<BrokerInstitutionsEntity, String> {

    @Aggregation(pipeline = {
            "{ $match : { brokerCode : ?0 } }",
            "{ $project: { institutionEntities : { $slice: ['$institutions', ?1, ?2] }, total: { $size: '$institutions' }, } }"
    })
    Optional<BrokerInstitutionAggregate> findPagedInstitutionsByBrokerCode(String brokerCode, Integer sliceStart, Integer size);

}
