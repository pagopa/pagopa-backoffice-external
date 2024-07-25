package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.CreditorInstitutionIbansEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditorInstitutionIbansRepository extends MongoRepository<CreditorInstitutionIbansEntity, String> {


    @Aggregation(pipeline = {"{$skip:  ?0}" , "{$limit:  ?1}" })
    List<CreditorInstitutionIbansEntity> findAll(Integer skip, Integer limit);


}
