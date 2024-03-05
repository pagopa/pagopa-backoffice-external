package it.pagopa.selfcare.pagopa.model.tavoloop;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OperativeTableEntitiesList {
    private List<OperativeTableEntity> tavoloOpOperationsList;
}
