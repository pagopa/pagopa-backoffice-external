package it.pagopa.selfcare.pagopa.model.tavoloop;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperativeTableResourceList {

    @ApiModelProperty(value = "All Tavolo operativo details", required = true)
    @JsonProperty(required = true)
    private List<OperativeTable> operativeTableList;
}
