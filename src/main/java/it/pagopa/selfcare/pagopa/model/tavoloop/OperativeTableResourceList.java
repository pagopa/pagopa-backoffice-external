package it.pagopa.selfcare.pagopa.model.tavoloop;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OperativeTableResourceList {

    @ApiModelProperty(value = "All Tavolo operativo details", required = true)
    @JsonProperty(required = true)
    private List<OperativeTable> operativeTableList;
}
