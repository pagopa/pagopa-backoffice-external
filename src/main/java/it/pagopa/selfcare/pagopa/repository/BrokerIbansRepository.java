package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrokerIbansRepository extends MongoRepository<BrokerIbansEntity, String> {

    @Query(value = "{ brokerCode : ?0 }", fields = "{ ibans: { $slice: [?1, ?2]}}")
    Optional<BrokerIbansEntity> getBrokerIbans(String brokerCode, Integer skip, Integer limit);

}
