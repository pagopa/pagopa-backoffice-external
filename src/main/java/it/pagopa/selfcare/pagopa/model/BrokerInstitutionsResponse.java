package it.pagopa.selfcare.pagopa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
public class BrokerInstitutionsResponse implements Serializable {

    @ApiModelProperty(value = "List of creditor institutions", required = true)
    @JsonProperty(required = true)
    private List<BrokerInstitutionResource> creditorInstitutions;
    @ApiModelProperty(value = "info pageable", required = true)
    @JsonProperty(required = true)
    private PageInfo pageInfo;

}
