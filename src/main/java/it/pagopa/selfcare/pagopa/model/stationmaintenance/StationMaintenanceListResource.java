package it.pagopa.selfcare.pagopa.model.stationmaintenance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Model class the response for station's maintenance APIs
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationMaintenanceListResource {

    @JsonProperty("station_maintenance_list")
    @Schema(description = "List of station's maintenance")
    @NotNull
    @Valid
    private List<StationMaintenanceResource> maintenanceList;

    @JsonProperty("page_info")
    @NotNull
    @Valid
    private PageInfo pageInfo;
}