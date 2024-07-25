package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.IbanAggregate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BrokerIbansRepository extends MongoRepository<BrokerIbansEntity, String> {

    @Aggregation(pipeline = {
        "{ $match : { brokerCode : ?0 } }",
        "{ $project: { ibansSlice : { $slice: ['$ibans', ?1, ?2] }, total: { $size: '$ibans' }, } }"
    })
    Optional<IbanAggregate> getBrokerIbans(String brokerCode, Integer sliceStart, Integer size);

}
