package ru.yakovlev05.cms.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PaymentResponseDto {
    private String id;
    private String status;
    private String description;
    private Confirmation confirmation;
    private Metadata metadata;

    @Data
    public static class Confirmation {
        private String type;

        @JsonProperty("confirmation_url")
        private String confirmationUrl;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Metadata {
        private String verifyKey;
    }
}
