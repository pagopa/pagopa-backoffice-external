package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionResource;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResource;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerIbansRepository;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import it.pagopa.selfcare.pagopa.service.ExternalService;
import it.pagopa.selfcare.pagopa.util.PageInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExternalServiceImpl implements ExternalService {

    private final BrokerInstitutionsRepository brokerInstitutionsRepository;

    private final BrokerIbansRepository brokerIbansRepository;

    public ExternalServiceImpl(BrokerInstitutionsRepository brokerInstitutionsRepository, BrokerIbansRepository brokerIbansRepository) {
        this.brokerInstitutionsRepository = brokerInstitutionsRepository;
        this.brokerIbansRepository = brokerIbansRepository;
    }


    @Override
    public BrokerInstitutionsResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page) {
        Optional<BrokerInstitutionsEntity> brokerInstitutionsEntity = brokerInstitutionsRepository
                .findPagedInstitutionsByBrokerCode(brokerCode, page == 0 ? 0 : ((page*limit)-1), limit);
        if (brokerInstitutionsEntity.isEmpty() ||
            brokerInstitutionsEntity.get().getInstitutions() == null) {
            throw new AppException(AppError.BROKER_INSTITUTIONS_NOT_FOUND, brokerCode);
        }
        return BrokerInstitutionsResponse
                .builder()
                .creditorInstitutions(brokerInstitutionsEntity.get().getInstitutions().stream().map(
                    brokerInstutitionEntity -> {
                        BrokerInstitutionResource response = new BrokerInstitutionResource();
                        BeanUtils.copyProperties(brokerInstutitionEntity, response);
                        return response;
                    }
                ).collect(Collectors.toList()))
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

    @Override
    public CIIbansResponse getBrokersIbans(Integer limit, Integer page) {
        Optional<BrokerIbansEntity> brokerIbanEntities = brokerIbansRepository.getMergedIbans(
                page == 0 ? 0 : ((page*limit)-1),limit);

        return CIIbansResponse
                .builder()
                .ibans(brokerIbanEntities.isPresent() && brokerIbanEntities.get().getIbans() != null ?
                        brokerIbanEntities.get().getIbans().stream().map(brokerIbanEntity -> {
                            CIIbansResource response = new CIIbansResource();
                            BeanUtils.copyProperties(brokerIbanEntity, response);
                            return response;
                        }).collect(Collectors.toList())
                        : Collections.emptyList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

    @Override
    public CIIbansResponse getBrokerIbans(String brokerCode, Integer limit, Integer page) {
        Optional<BrokerIbansEntity> brokerIbanEntities = brokerIbansRepository.getBrokerIbans(
                brokerCode, page == 0 ? 0 : ((page*limit)-1),limit);
        if (brokerIbanEntities.isEmpty() ||
                brokerIbanEntities.get().getIbans() == null) {
            throw new AppException(AppError.BROKER_IBANS_NOT_FOUND, brokerCode);
        }
        return CIIbansResponse
                .builder()
                .ibans(brokerIbanEntities.get().getIbans() != null ?
                        brokerIbanEntities.get().getIbans().stream().map(brokerIbanEntity -> {
                            CIIbansResource response = new CIIbansResource();
                            BeanUtils.copyProperties(brokerIbanEntity, response);
                            return response;
                        }).collect(Collectors.toList())
                        : Collections.emptyList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

}
