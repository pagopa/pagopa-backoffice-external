package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionAggregate;
import it.pagopa.selfcare.pagopa.entities.IbanAggregate;
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
        Optional<BrokerInstitutionAggregate> brokerInstitutionsEntity = brokerInstitutionsRepository
                .findPagedInstitutionsByBrokerCode(brokerCode, page == 0 ? 0 : ((page * limit) - 1), limit);
        if (brokerInstitutionsEntity.isEmpty() ||
                brokerInstitutionsEntity.get().getInstitutionEntities() == null) {
            throw new AppException(AppError.BROKER_INSTITUTIONS_NOT_FOUND, brokerCode);
        }
        return BrokerInstitutionsResponse
                .builder()
                .creditorInstitutions(brokerInstitutionsEntity.get().getInstitutionEntities().stream().map(
                        brokerInstutitionEntity -> {
                            BrokerInstitutionResource response = new BrokerInstitutionResource();
                            BeanUtils.copyProperties(brokerInstutitionEntity, response);
                            return response;
                        }
                ).toList())
                .pageInfo(PageInfoMapper.toPageInfo(page, limit,
                        (int) Math.ceil((double) brokerInstitutionsEntity.get().getTotal() / limit),
                        brokerInstitutionsEntity.get().getTotal()))
                .build();
    }

    @Override
    public CIIbansResponse getCIsIbans(Integer limit, Integer page) {
        var creditorInstitutionIbansEntities = creditorInstitutionIbansRepository.findAll(page * limit, limit);
        long totalDocuments = creditorInstitutionIbansRepository.count();

        return CIIbansResponse
                .builder()
                .ibans(creditorInstitutionIbansEntities.stream()
                        .map(brokerIbanEntity -> {
                            CIIbansResource response = new CIIbansResource();
                            BeanUtils.copyProperties(brokerIbanEntity, response);
                            return response;
                        })
                        .toList())
                .pageInfo(PageInfoMapper.toPageInfo(
                        page, limit,
                        (int) Math.ceil((double) totalDocuments / limit),
                        totalDocuments))
                .build();
    }

    @Override
    public CIIbansResponse getBrokerIbans(String brokerCode, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page, limit);
        Optional<IbanAggregate> brokerIbanAggregate = brokerIbansRepository.getBrokerIbans(
                brokerCode, page == 0 ? 0 : ((page * limit) - 1), limit);
        if (brokerIbanAggregate.isEmpty() ||
                brokerIbanAggregate.get().getIbansSlice() == null) {
            throw new AppException(AppError.BROKER_IBANS_NOT_FOUND, brokerCode);
        }
        return CIIbansResponse
                .builder()
                .ibans(brokerIbanAggregate.get().getIbansSlice().stream().map(brokerIbanEntity -> {
                    CIIbansResource response = new CIIbansResource();
                    BeanUtils.copyProperties(brokerIbanEntity, response);
                    return response;
                }).toList())
                .pageInfo(PageInfoMapper.toPageInfo(
                        page, limit,
                        (int) Math.ceil((double) brokerIbanAggregate.get().getTotal() / limit),
                        brokerIbanAggregate.get().getTotal()))
                .build();
    }

}
