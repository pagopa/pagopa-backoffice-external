package it.pagopa.selfcare.pagopa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class BrokerInstitutionsResponse implements Serializable {

    @Schema(description = "List of creditor institutions", required = true)
    @JsonProperty(required = true)
    private List<BrokerInstitutionResource> creditorInstitutions;
    @Schema(description = "info pageable", required = true)
    @JsonProperty(required = true)
    private PageInfo pageInfo;

}
