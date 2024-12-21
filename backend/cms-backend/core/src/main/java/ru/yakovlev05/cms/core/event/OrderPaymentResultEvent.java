package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPaymentResultEvent {
    private String orderId;
    private PaymentResult paymentStatus;

    public enum PaymentResult {
        SUCCEED,
        CANCELED
    }
}
