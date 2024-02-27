package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.service.BrokersService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class BrokerController {

    private final BrokersService brokersService;

    public BrokerController(BrokersService brokersService) {
        this.brokersService = brokersService;
    }

    @Operation(summary = "getBrokerIbans", description = "Return merged Broker Ibans List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/brokers/ibans")
    public BrokerIbansResponse getBrokerIbans(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        return brokersService.getBrokersIbans(limit, page);
    }

    @Operation(summary = "getBrokerInstitutions", description = "Return Broker Creditor Institution List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/brokers/{brokerCode}/creditor_institutions")
    public BrokerInstitutionsResponse getCreditorInstitutions(@PathVariable("brokerCode") String brokerCode,
                                                              @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        return brokersService.getBrokerInstitutions(brokerCode, limit, page);
    }

}
