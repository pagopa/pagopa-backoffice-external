package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.CiIbanAggregate;
import it.pagopa.selfcare.pagopa.entities.CreditorInstitutionIbansEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditorInstitutionIbansRepository extends MongoRepository<CreditorInstitutionIbansEntity, String> {


    @Aggregation(pipeline = {
        "{ $facet: { metadata: [ { $count: 'totalCount'}],data: [{$skip: ?0}, {$limit: ?1}] } }"
    })
    CiIbanAggregate findAll(Integer skip, Integer limit);


}
