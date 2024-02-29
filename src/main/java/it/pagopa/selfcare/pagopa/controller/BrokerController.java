package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.pagopa.selfcare.pagopa.model.BrokerIbansResponse;
import it.pagopa.selfcare.pagopa.model.BrokerInstitutionsResponse;
import it.pagopa.selfcare.pagopa.service.BrokersService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Instance of a estController that defines the endpoints related to broker data retrieval
 */
@RestController
@Validated
public class BrokerController {

    private final BrokersService brokersService;

    public BrokerController(BrokersService brokersService) {
        this.brokersService = brokersService;
    }


    /**
     * Retreive a paged list of broker related ibans
     * @param limit number of elements per page
     * @param page page to be selected
     * @return instance of BrokerIbansResponse, containing a paged list of ibans
     */
    @Operation(summary = "getBrokerIbans", description = "Return merged Broker Ibans List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/brokers/ibans")
    @Cacheable(value = "brokerIbans")
    public BrokerIbansResponse getBrokerIbans(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        return brokersService.getBrokersIbans(limit, page);
    }

    /**
     * Retrieve a paged list of creditor institutions, using a specific broker code
     * @param brokerCode broker code to be used as filter
     * @param limit number of elements per page
     * @param page page to be used
     * @return paged list od broker related creditor institutions, filtered by code
     */
    @Operation(summary = "getBrokerInstitutions", description = "Return Broker Creditor Institution List")
    @OpenApiTableMetadata(readWriteIntense = OpenApiTableMetadata.ReadWrite.READ, cacheable = true)
    @GetMapping("/brokers/{brokerCode}/creditor_institutions")
    @Cacheable(value = "brokerInstitutions")
    public BrokerInstitutionsResponse getCreditorInstitutions(@PathVariable("brokerCode") String brokerCode,
                                                              @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        return brokersService.getBrokerInstitutions(brokerCode, limit, page);
    }

}
