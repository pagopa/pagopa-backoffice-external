package it.pagopa.selfcare.pagopa.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record TestResponse(
        @JsonProperty("testResponseField")
        @NotNull
        @Schema(
                example = "test",
                description = "A test response field")
        String testResponseField
) {
}