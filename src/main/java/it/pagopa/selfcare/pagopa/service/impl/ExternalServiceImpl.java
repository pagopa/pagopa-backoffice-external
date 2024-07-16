package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.entities.CreditorInstitutionIbansEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionResource;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.CIIbansResource;
import it.pagopa.selfcare.pagopa.model.CIIbansResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerIbansRepository;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import it.pagopa.selfcare.pagopa.repository.CreditorInstitutionIbansRepository;
import it.pagopa.selfcare.pagopa.service.ExternalService;
import it.pagopa.selfcare.pagopa.util.PageInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalServiceImpl implements ExternalService {

    private final BrokerInstitutionsRepository brokerInstitutionsRepository;

    private final BrokerIbansRepository brokerIbansRepository;

    private final CreditorInstitutionIbansRepository creditorInstitutionIbansRepository;

    public ExternalServiceImpl(BrokerInstitutionsRepository brokerInstitutionsRepository, BrokerIbansRepository brokerIbansRepository, CreditorInstitutionIbansRepository creditorInstitutionIbansRepository) {
        this.brokerInstitutionsRepository = brokerInstitutionsRepository;
        this.brokerIbansRepository = brokerIbansRepository;
        this.creditorInstitutionIbansRepository = creditorInstitutionIbansRepository;
    }


    @Override
    public BrokerInstitutionsResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page) {
        Optional<BrokerInstitutionsEntity> brokerInstitutionsEntity = brokerInstitutionsRepository
                .findPagedInstitutionsByBrokerCode(brokerCode, page == 0 ? 0 : ((page * limit) - 1), limit);
        if(brokerInstitutionsEntity.isEmpty() ||
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
                ).toList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

    @Override
    public CIIbansResponse getBrokersIbans(Integer limit, Integer page) {
//        int skip = page == 0 ? 0 : ((page * limit) - 1);
        Pageable pageable = PageRequest.of(page, limit);
        List<CreditorInstitutionIbansEntity> creditorInstitutionIbansEntities = creditorInstitutionIbansRepository.getCreditorInstitutionIbansEntities(pageable);

        return CIIbansResponse
                .builder()
                .ibans(creditorInstitutionIbansEntities.stream()
                        .map(brokerIbanEntity -> {
                            CIIbansResource response = new CIIbansResource();
                            BeanUtils.copyProperties(brokerIbanEntity, response);
                            return response;
                        })
                        .toList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

    @Override
    public CIIbansResponse getBrokerIbans(String brokerCode, Integer limit, Integer page) {
        Optional<BrokerIbansEntity> brokerIbanEntities = brokerIbansRepository.getBrokerIbans(
                brokerCode, page == 0 ? 0 : ((page * limit) - 1), limit);
        if(brokerIbanEntities.isEmpty() ||
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
                        }).toList()
                        : Collections.emptyList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

}
