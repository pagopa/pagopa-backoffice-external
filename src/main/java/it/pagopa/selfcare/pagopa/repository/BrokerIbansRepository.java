package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.BrokerIbanEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrokerIbansRepository extends MongoRepository<BrokerIbansEntity, String> {

    @Aggregation(pipeline = {
            "{ $unwind: \"$ibans\", }",
            "{$group: { _id: \"$ibans.iban\", iban: { $first: \"$ibans.iban\", }," +
            " ciFiscalCode: { $first: \"$ibans.ciFiscalCode\", }, ciName: { $first: \"$ibans.ciName\", }, " +
            " description: {  $first: \"$ibans.description\", }, label: { $first: \"$ibans.label\", }, " +
            " status: { $first: \"$ibans.status\", }, validityDate: { $first: \"$ibans.validityDate\", }, " +
            "}, }",
            "{ $skip: ?0, }",  "{ $limit: ?1, }",
            "{ $group: { _id: null, ibans: { $push: \"$$ROOT\", }, }, }",
            "{ $project: { _id: 0, }, }"
    })
    Optional<BrokerIbansEntity> getMergedIbans(int skip, Integer limit);

}
