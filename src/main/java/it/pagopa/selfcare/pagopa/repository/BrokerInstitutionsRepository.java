package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrokerInstitutionsRepository extends MongoRepository<BrokerInstitutionsEntity, String> {

    @Query(value = "{ brokerCode : ?0 }", fields = "{ institutions: { $slice: [?1, ?2]}}")
    Optional<BrokerInstitutionsEntity> findPagedInstitutionsByBrokerCode(String brokerCode, int skip, Integer limit);

}
