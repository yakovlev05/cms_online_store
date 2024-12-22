package ru.yakovlev05.cms.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.order.dto.OrderClientRequestDto;
import ru.yakovlev05.cms.order.dto.OrderCreateClientResponseDto;
import ru.yakovlev05.cms.order.dto.OrderInfoClientResponseDto;
import ru.yakovlev05.cms.order.service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderCreateClientResponseDto createOrder(
            @RequestBody OrderClientRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(request, userDetails);
    }

    @GetMapping("/{order-id}")
    public OrderInfoClientResponseDto getOrderInfo(@PathVariable(name = "order-id") String orderId) {
        return orderService.getOrderInfo(orderId);
    }
}
