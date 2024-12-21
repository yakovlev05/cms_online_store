package ru.yakovlev05.cms.order.service;

import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.order.dto.OrderClientRequestDto;
import ru.yakovlev05.cms.order.dto.OrderClientResponseDto;
import ru.yakovlev05.cms.order.entity.Order;

public interface OrderService {
    OrderClientResponseDto createOrder(OrderClientRequestDto request, UserDetailsImpl userDetails);

    Order getById(String orderId);

    void save(Order order);
}
