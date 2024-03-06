package it.pagopa.selfcare.pagopa.mapper;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTable;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableEntitiesList;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;

public class OperativeTableMapper {

    public static OperativeTable toResource(OperativeTableEntity otEntity) {
        if (otEntity == null) {
            return null;
        }

        return OperativeTable.builder()
                .name(otEntity.getName())
                .email(otEntity.getEmail())
                .createdBy(otEntity.getCreatedBy())
                .createdAt(otEntity.getCreatedAt())
                .referent(otEntity.getReferent())
                .modifiedAt(otEntity.getModifiedAt())
                .modifiedBy(otEntity.getModifiedBy())
                .telephone(otEntity.getTelephone())
                .taxCode(otEntity.getTaxCode())
                .build();
    }

    public static OperativeTableResourceList toResource(OperativeTableEntitiesList otEntitiesList) {
        if (otEntitiesList == null) {
            return null;
        }
        return OperativeTableResourceList.builder()
                .operativeTableList(
                        otEntitiesList.getTavoloOpOperationsList().stream()
                                .map(OperativeTableMapper::toResource)
                                .toList())
                .build();
    }

    private OperativeTableMapper() {
    }
}
