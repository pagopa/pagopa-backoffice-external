package it.pagopa.selfcare.pagopa.repository;


import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.model.institutions.services.Consent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InstitutionServiceRtpConsentRepository extends MongoRepository<InstitutionConsentEntity, String> {


    @Query("{'consentDate': {'$gte': ?0,'$lte': ?1}, 'consent': '?2'}")
    List<InstitutionConsentEntity> findByDateAndConsent(Instant fromDate, Instant toDate, Consent consent, Pageable pageable);

}
