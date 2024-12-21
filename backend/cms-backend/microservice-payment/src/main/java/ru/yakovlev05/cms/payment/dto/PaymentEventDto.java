package ru.yakovlev05.cms.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class PaymentEventDto {
    private String type;
    private EventType event;
    private Object object;

    @Data
    public static class Object {
        private String id;
        private boolean paid;

        @JsonProperty("captured_at")
        private Date capturedAt;

        private Metadata metadata;
    }

    public enum EventType {

        @JsonProperty("payment.succeeded")
        PAYMENT_SUCCEEDED,

        @JsonProperty("payment.cancelled")
        PAYMENT_CANCELLED,
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Metadata{
        private String verifyKey;
    }
}
