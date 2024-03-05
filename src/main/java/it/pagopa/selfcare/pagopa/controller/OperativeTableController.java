package it.pagopa.selfcare.pagopa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;
import it.pagopa.selfcare.pagopa.service.OperativeTableService;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/operative_tables")
@Tag(name = "Operative Tables")
public class OperativeTableController {

    private final OperativeTableService operativeTableService;

    @Autowired
    public OperativeTableController(OperativeTableService operativeTableService) {
        this.operativeTableService = operativeTableService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get All operative tables", security = {@SecurityRequirement(name = "ApiKey")})
    @OpenApiTableMetadata
    public OperativeTableResourceList getOperativeTables() {
        return this.operativeTableService.getOperativeTables();
    }
}
