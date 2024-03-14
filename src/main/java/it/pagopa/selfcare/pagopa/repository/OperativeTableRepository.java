package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperativeTableRepository extends MongoRepository<OperativeTableEntity, String> {

    /**
     * Retrieve the list of all operative tables, optionally the list can be filtered by business name and tax code
     *
     * @param taxCode the tax code to be used for filter
     * @param name the business name to be used for filter
     * @return  a list of operative tables
     */
    List<OperativeTableEntity> findByTaxCodeLikeAndNameLikeIgnoreCase(String taxCode, String name);
}
