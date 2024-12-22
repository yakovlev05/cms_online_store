package ru.yakovlev05.cms.order.service;

import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.order.dto.OrderClientRequestDto;
import ru.yakovlev05.cms.order.dto.OrderCreateClientResponseDto;
import ru.yakovlev05.cms.order.dto.OrderInfoClientResponseDto;
import ru.yakovlev05.cms.order.entity.Order;

public interface OrderService {
    OrderCreateClientResponseDto createOrder(OrderClientRequestDto request, UserDetailsImpl userDetails);

    Order getById(String orderId);

    void save(Order order);

    OrderInfoClientResponseDto getOrderInfo(String orderId);
}
