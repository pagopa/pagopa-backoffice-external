package it.pagopa.selfcare.pagopa.repository;


import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import it.pagopa.selfcare.pagopa.model.institutions.services.Consent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface InstitutionServiceRtpConsentRepository extends MongoRepository<InstitutionConsentEntity, String> {


    @Query("{'consentDate': {'$gte': '?0','$lte': '?1'}, 'consent': '?2'}")
    List<InstitutionConsentEntity> findByDateAndConsent(OffsetDateTime fromDate, OffsetDateTime toDate, Consent consent, Pageable pageable);

    @Query(value = "{'consentDate': {'$gte': '?0','$lte': '?1'}, 'consent': '?2'}", count = true)
    Long countByDateAndConsent(OffsetDateTime fromDate, OffsetDateTime toDate, Consent consent);

}
