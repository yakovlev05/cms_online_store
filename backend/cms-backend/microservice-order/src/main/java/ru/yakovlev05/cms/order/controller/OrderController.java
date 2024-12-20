package ru.yakovlev05.cms.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.order.dto.OrderClientRequestDto;
import ru.yakovlev05.cms.order.dto.OrderClientResponseDto;
import ru.yakovlev05.cms.order.service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderClientResponseDto createOrder(
            @RequestBody OrderClientRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(request, userDetails);
    }
}
