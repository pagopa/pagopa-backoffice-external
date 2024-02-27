package it.pagopa.selfcare.pagopa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrokerIbansResponse {

    private List<BrokerIbansResource> ibans;
    private PageInfo pageInfo;

}
