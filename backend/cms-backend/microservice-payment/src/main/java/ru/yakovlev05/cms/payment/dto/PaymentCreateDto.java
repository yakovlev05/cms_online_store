package ru.yakovlev05.cms.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.payment.entity.PaymentCurrency;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentCreateDto {
    private Amount amount;
    private boolean capture;
    private Confirmation confirmation;
    private String description;
    private Metadata metadata;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Amount {
        private String value;
        private PaymentCurrency currency;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Confirmation {
        private String type;

        @JsonProperty("return_url")
        private String returnUrl;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Metadata{
        private String verifyKey;
    }
}
