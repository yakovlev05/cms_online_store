package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderValidationResultEvent {
    private String orderId;
    private OrderValidationStatus validationStatus;
    private BigDecimal cost;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Product {
        private long productOrderId;
        private long originalId;
        private String name;
        private BigDecimal price;
    }
}
