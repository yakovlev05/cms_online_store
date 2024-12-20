package ru.yakovlev05.cms.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.order.entity.OrderStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderClientResponseDto {
    private String orderId;
    private OrderStatus status;
}
