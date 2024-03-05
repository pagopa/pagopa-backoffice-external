package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperativeTableRepository extends MongoRepository<OperativeTableEntity, String> {
}
