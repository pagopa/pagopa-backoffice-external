package it.pagopa.selfcare.pagopa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable {

    @JsonProperty("page")
    @Schema(description = "page number (0 is the first page)", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer page;

    @JsonProperty("limit")
    @Schema(description = "number of elements per page", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer limit;

}
