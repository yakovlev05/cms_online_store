package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderValidationInputEvent {
    private String orderId;
    private List<Product> productIds;

    @Data
    @AllArgsConstructor
    public static class Product{
        private long id;
        private long originalProductId;
        private int count;
    }
}
