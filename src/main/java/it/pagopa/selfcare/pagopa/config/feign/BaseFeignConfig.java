package it.pagopa.selfcare.pagopa.config.feign;

import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;

public abstract class BaseFeignConfig {

    private static final String HEADER_REQUEST_ID = "X-Request-Id";

    @Bean
    public RequestInterceptor commonHeaderInterceptor() {
        return requestTemplate -> requestTemplate
                .header(HEADER_REQUEST_ID, MDC.get("requestId"));
    }

}
