package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.CreditorInstitutionIbansEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditorInstitutionIbansRepository extends MongoRepository<CreditorInstitutionIbansEntity, String> {

    List<CreditorInstitutionIbansEntity> getCreditorInstitutionIbansEntities(Pageable pageable);

}
