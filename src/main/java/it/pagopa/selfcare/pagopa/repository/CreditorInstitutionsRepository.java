package it.pagopa.selfcare.pagopa.repository;

import it.pagopa.selfcare.pagopa.model.CreditorInstitutionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class CreditorInstitutionsRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public CreditorInstitutionsResponse findPaginatedInstitutions(int skip, int limit, Boolean hasCbill) {

        // filteringPipeline is a list of all the stage that MongoDB will have to perform, in order.
        List<AggregationOperation> filteringPipeline = new ArrayList<>();

        // Stages 1-4: Filter down to a stream of valid individual institutions.
        filteringPipeline.add(unwind("institutions"));

        // Stage 2: CBILL filter, optional check on CBILL code existence.
        if(hasCbill != null) {
            if(hasCbill) {
                filteringPipeline.add(match(Criteria.where("institutions.cbillCode").exists(true)));
            } else {
                filteringPipeline.add(match(Criteria.where("institutions.cbillCode").exists(false)));
            }
        }

        // Stage 3: LEFT JOIN between brokerInstitutions and creditorInstitutionIbans based on institutions.taxCode.
        LookupOperation lookup = LookupOperation.newLookup()
                .from("creditorInstitutionIbans")
                // Join condition: The join occurs when the value of the institutions.taxCode field (in the left document)
                // is equal to the value of the ciFiscalCode field (in a document in the right collection).
                .localField("institutions.taxCode")
                .foreignField("ciFiscalCode")
                // If a match is found, the validIbans array is populated.
                .as("validIbans");
        filteringPipeline.add(lookup);

        // Stage 4: Filter all documents for which the Join found a valid IBAN.
        filteringPipeline.add(match(Criteria.where("validIbans.0").exists(true)));

        // Stage 5: Use $facet to get both total count and paginated data.
        FacetOperation facetOperation = facet()
                .and(
                        // Sub-pipeline 1: Get the total count of all matching institutions
                        count().as("total")
                )
                .as("totalCount")
                .and(
                        // Sub-pipeline 2: Get the paginated and projected list of institutions
                        skip((long) skip),
                        limit((long) limit),
                        project()
                                .and("institutions.companyName").as("companyName")
                                .and("institutions.taxCode").as("taxCode")
                )
                .as("institutionEntities");

        filteringPipeline.add(facetOperation);

        // Stage 6: Clean up the $facet output
        ProjectionOperation finalProjection = project("institutionEntities")
                .and(ConditionalOperators.ifNull(
                        ArrayOperators.ArrayElemAt.arrayOf("totalCount.total")
                ).then(0L)) // Get the count, or 0 if no results
                .as("total");

        filteringPipeline.add(finalProjection);

        // Execute the aggregation
        Aggregation aggregation = newAggregation(filteringPipeline);

        return mongoTemplate.aggregate(aggregation, "brokerInstitutions", CreditorInstitutionsResponse.class)
                .getUniqueMappedResult();
    }
}

