package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionEntity;
import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionsEntity;
import it.pagopa.selfcare.pagopa.exception.AppError;
import it.pagopa.selfcare.pagopa.exception.AppException;
import it.pagopa.selfcare.pagopa.model.GetEcResponse;
import it.pagopa.selfcare.pagopa.model.PageInfo;
import it.pagopa.selfcare.pagopa.repository.BrokerInstitutionsRepository;
import it.pagopa.selfcare.pagopa.service.InstitutionsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionsServiceImpl implements InstitutionsService {

    private final BrokerInstitutionsRepository brokerInstitutionsRepository;

    public InstitutionsServiceImpl(BrokerInstitutionsRepository brokerInstitutionsRepository) {
        this.brokerInstitutionsRepository = brokerInstitutionsRepository;
    }

    @Override
    public GetEcResponse getBrokerInstitutions(String brokerCode, Integer limit, Integer page) {
        List<BrokerInstitutionsEntity> brokerInstitutionsEntityList = brokerInstitutionsRepository
                .findPagedInstitutionsByBrokerCode(brokerCode, page == 0 ? 0 : ((page*limit)-1), limit);
        if (brokerInstitutionsEntityList == null || brokerInstitutionsEntityList.isEmpty()) {
            throw new AppException(AppError.BROKER_NOT_FOUND, brokerCode);
        }
        return GetEcResponse
                .builder()
                .creditorInstitutions(brokerInstitutionsEntityList.get(0).getInstitutions())
                .pageInfo(PageInfo.builder().page(page).limit(limit).build())
                .build();
    }

}
