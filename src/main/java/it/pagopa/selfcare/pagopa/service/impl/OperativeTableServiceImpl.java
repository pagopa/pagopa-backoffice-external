package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import it.pagopa.selfcare.pagopa.mapper.OperativeTableMapper;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableEntitiesList;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;
import it.pagopa.selfcare.pagopa.repository.OperativeTableRepository;
import it.pagopa.selfcare.pagopa.service.OperativeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperativeTableServiceImpl implements OperativeTableService {

    private final OperativeTableRepository operativeTableRepository;

    @Autowired
    public OperativeTableServiceImpl(OperativeTableRepository operativeTableRepository) {
        this.operativeTableRepository = operativeTableRepository;
    }

    @Override
    public OperativeTableResourceList getOperativeTables() {
        List<OperativeTableEntity> entities = this.operativeTableRepository.findAll();

        OperativeTableEntitiesList operativeTableEntitiesList = OperativeTableEntitiesList.builder()
                    .tavoloOpOperationsList(entities)
                .build();
        return OperativeTableMapper.toResource(operativeTableEntitiesList);
    }
}