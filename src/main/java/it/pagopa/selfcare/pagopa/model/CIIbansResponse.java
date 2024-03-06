package it.pagopa.selfcare.pagopa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CIIbansResponse implements Serializable {

    @Schema(description = "list of IBANs", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CIIbansResource> ibans;

    @Schema(description = "information about pagination", requiredMode = Schema.RequiredMode.REQUIRED)
    private PageInfo pageInfo;

}
