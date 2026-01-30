package it.pagopa.selfcare.pagopa.model.institutions.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.selfcare.pagopa.util.Constants;
import it.pagopa.selfcare.pagopa.util.OffsetDateTimeDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsentInfo {

    @JsonProperty("consent")
    @NotNull
    @Schema(
            example = "OTP_OUT",
            description = "The expressed consent")
    private Consent consent;

    @JsonProperty("date")
    @NotNull
    @JsonFormat(pattern = Constants.ZONED_DATE_TIME_FORMAT)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    @Schema(
            example = "2024-04-01T13:00:00.000+02:00",
            description = "The date when consent have been expressed")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;
}
