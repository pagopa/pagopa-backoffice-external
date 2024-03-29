package it.pagopa.selfcare.pagopa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEcResponse implements Serializable {

    @Schema(description = "List of creditor institutions", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(required = true)
    private List<BrokerInstitutionEntity> creditorInstitutions;

    @Schema(description = "information about pagination", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(required = true)
    private PageInfo pageInfo;

}
