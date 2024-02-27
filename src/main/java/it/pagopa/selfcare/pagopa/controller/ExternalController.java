package it.pagopa.selfcare.pagopa.controller;

import it.pagopa.selfcare.pagopa.model.GetEcResponse;
import it.pagopa.selfcare.pagopa.service.InstitutionsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ExternalController {

    private final InstitutionsService institutionsService;

    public ExternalController(InstitutionsService institutionsService) {
        this.institutionsService = institutionsService;
    }

    @GetMapping("/brokers/{brokerCode}/creditor_institutions")
    public GetEcResponse getCreditorInstitutions(@PathVariable("brokerCode") String brokerCode,
                                                 @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page) {
        return institutionsService.getBrokerInstitutions(brokerCode, limit, page);
    }

}
