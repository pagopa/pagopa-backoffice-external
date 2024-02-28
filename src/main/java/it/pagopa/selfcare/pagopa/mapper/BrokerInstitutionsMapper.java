package it.pagopa.selfcare.pagopa.mapper;

import it.pagopa.selfcare.pagopa.entities.BrokerInstitutionEntity;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionResource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that contains utility mapping methods to convert a BrokerInstitutionEntity into a BrokerInstitutionResource
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrokerInstitutionsMapper {

    public static BrokerInstitutionResource toResource(BrokerInstitutionEntity broker) {
        if(broker == null) {
            return null;
        }
        BrokerInstitutionResource response = new BrokerInstitutionResource();
        BeanUtils.copyProperties(broker, response);
        return response;
    }

    public static List<BrokerInstitutionResource> toResources(List<BrokerInstitutionEntity> brokerInstitutionEntityList) {
        if(brokerInstitutionEntityList == null) {
            return null;
        }
        return brokerInstitutionEntityList.stream()
                .map(BrokerInstitutionsMapper::toResource)
                .collect(Collectors.toList());
    }

}
