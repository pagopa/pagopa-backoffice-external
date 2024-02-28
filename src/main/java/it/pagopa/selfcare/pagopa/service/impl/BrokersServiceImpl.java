package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerIbanEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerIbansEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.mapper.BrokerIbansMapper;
import it.pagopa.selfcare.pagopa.mapper.BrokerInstitutionsMapper;
import it.pagopa.selfcare.pagopa.mapper.PageInfoMapper;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;
import it.pagopa.selfcare.pagopa.repository.BrokerIbansRepository;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import it.pagopa.selfcare.pagopa.service.BrokersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokersServiceImpl implements BrokersService {

    private final BrokerInstitutionsRepository brokerInstitutionsRepository;

    private final BrokerIbansRepository brokerIbansRepository;

    public BrokersServiceImpl(BrokerInstitutionsRepository brokerInstitutionsRepository, BrokerIbansRepository brokerIbansRepository) {
        this.brokerInstitutionsRepository = brokerInstitutionsRepository;
        this.brokerIbansRepository = brokerIbansRepository;
    }

    @Override
    public BrokerInstitutionsResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page) {
        List<BrokerInstitutionsEntity> brokerInstitutionsEntityList = brokerInstitutionsRepository
                .findPagedInstitutionsByBrokerCode(brokerCode, page == 0 ? 0 : ((page*limit)-1), limit);
        if (brokerInstitutionsEntityList == null ||
                brokerInstitutionsEntityList.isEmpty() ||
                brokerInstitutionsEntityList.get(0).getInstitutions() == null) {
            throw new AppException(AppError.BROKER_NOT_FOUND, brokerCode);
        }
        return BrokerInstitutionsResponse
                .builder()
                .creditorInstitutions(BrokerInstitutionsMapper
                        .toResources(brokerInstitutionsEntityList.get(0).getInstitutions()))
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

    @Override
    public BrokerIbansResponse getBrokersIbans(Integer limit, Integer page) {
        List<BrokerIbansEntity> brokerIbanEntities = brokerIbansRepository.getMergedIbans(
                page == 0 ? 0 : ((page*limit)-1),limit);

        return BrokerIbansResponse
                .builder()
                .ibans(BrokerIbansMapper.toResources(brokerIbanEntities.get(0).getIbans()))
                .pageInfo(PageInfoMapper.toPageInfo(page, limit))
                .build();
    }

}
