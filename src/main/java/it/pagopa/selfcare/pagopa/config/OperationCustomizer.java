package it.pagopa.selfcare.pagopa.config;

import io.swagger.v3.oas.models.Operation;
import it.pagopa.selfcare.pagopa.util.OpenApiTableMetadata;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import static it.pagopa.selfcare.pagopa.util.CommonUtility.deNull;


@Component
public class OperationCustomizer implements GlobalOperationCustomizer {
    String TABLE_TEMPLATE =
            "Internal | External | Synchronous | Authorization | Authentication | TPS | Idempotency | Stateless | Read/Write Intense | Cacheable\n" +
                    "-|-|-|-|-|-|-|-|-|-\n";

    private static String buildData(OpenApiTableMetadata annotation) {
        return parseBoolToYN(annotation.internal())
                + " | " + parseBoolToYN(annotation.external())
                + " | " + parseBoolToYN(annotation.synchronous())
                + " | " + annotation.authorization()
                + " | " + annotation.authentication()
                + " | " + annotation.tps() + "/sec"
                + " | " + parseBoolToYN(annotation.idempotency())
                + " | " + parseBoolToYN(annotation.stateless())
                + " | " + parseReadWrite(annotation.readWriteIntense())
                + " | " + parseBoolToYN(annotation.cacheable());
    }

    private static String parseReadWrite(OpenApiTableMetadata.ReadWrite readWrite) {
        return readWrite.getValue();
    }

    private static String parseBoolToYN(boolean value) {
        return value ? "Y" : "N";
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        OpenApiTableMetadata annotation = handlerMethod.getMethodAnnotation(OpenApiTableMetadata.class);
        if(annotation != null) {
            operation.description(TABLE_TEMPLATE + buildData(annotation) + "\n" + deNull(operation.getDescription()));
        }
        return operation;
    }
}
