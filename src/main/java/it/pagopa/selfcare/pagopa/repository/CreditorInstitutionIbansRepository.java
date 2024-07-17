package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.CreditorInstitutionIbansEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditorInstitutionIbansRepository extends MongoRepository<CreditorInstitutionIbansEntity, String> {


}
