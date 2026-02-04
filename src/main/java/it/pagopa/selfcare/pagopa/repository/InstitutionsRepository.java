package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.InstitutionConsentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;

@Repository
public interface InstitutionsRepository extends MongoRepository<InstitutionConsentEntity,String> {

    /*
        Find all the institution's consents by serviceId, return a Page object
     */
    Page<InstitutionConsentEntity> getInstitutionConsents(Pageable pageable);

    /*
        Find all the institution's consents by serviceId filtered by starting date and ending date, return a Page object
     */
    Page<InstitutionConsentEntity> findInstitutionConsentsByConsentDateBetween(OffsetDateTime startingDate, OffsetDateTime finalDate, Pageable pageable);

}
