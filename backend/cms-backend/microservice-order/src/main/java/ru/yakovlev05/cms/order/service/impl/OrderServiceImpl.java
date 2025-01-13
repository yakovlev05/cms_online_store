package ru.yakovlev05.cms.order.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.order.dto.OrderClientRequestDto;
import ru.yakovlev05.cms.order.dto.OrderCreateClientResponseDto;
import ru.yakovlev05.cms.order.dto.OrderInfoClientResponseDto;
import ru.yakovlev05.cms.order.entity.*;
import ru.yakovlev05.cms.order.repository.OrderRepository;
import ru.yakovlev05.cms.order.service.CommunicationMethodService;
import ru.yakovlev05.cms.order.service.KafkaService;
import ru.yakovlev05.cms.order.service.OrderService;
import ru.yakovlev05.cms.order.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final CommunicationMethodService communicationMethodService;

    private final KafkaService kafkaService;

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderCreateClientResponseDto createOrder(OrderClientRequestDto request, UserDetailsImpl userDetails) {
        User user = userDetails == null ? null : userService.getById(userDetails.getId());
        BigDecimal productsCost = getProductsCost(request.getProducts());
        Order order = fillOrderFromDto(request, user, productsCost, BigDecimal.ZERO);
        order.getProducts().forEach(product -> product.setOrder(order));

        orderRepository.save(order);

        if (user != null) {
            user.getOrders().add(order);
            userService.save(user);
        }


        log.info("Order created with id: {}", order.getId());

        kafkaService.sendOrderValidationInputEvent(order);

        return new OrderCreateClientResponseDto(order.getId(), order.getStatus());
    }

    @Override
    public Order getById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public OrderInfoClientResponseDto getOrderInfo(String orderId) {
        Order order = getById(orderId);
        return fillDto(order);
    }

    @Transactional
    @Override
    public List<OrderInfoClientResponseDto> getMyOrders(String id) {
        User user = userService.getById(id);
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(this::fillDto)
                .toList();
    }

    private BigDecimal getProductsCost(List<OrderClientRequestDto.Product> products) {
        return products.stream()
                .map(OrderClientRequestDto.Product::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<Product> getProducts(List<OrderClientRequestDto.Product> productList) {
        return productList.stream()
                .map(product -> Product.builder()
                        .productId(product.getId())
                        .count(product.getCount())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

    private Order fillOrderFromDto(OrderClientRequestDto request, User user,
                                   BigDecimal productsCost, BigDecimal deliveryCost) {
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .products(getProducts(request.getProducts()))
                .customerInfo(CustomerInfo.builder()
                        .firstName(request.getCustomerInfo().getFirstName())
                        .secondName(request.getCustomerInfo().getSecondName())
                        .patronymic(request.getCustomerInfo().getPatronymic())
                        .phoneNumber(request.getCustomerInfo().getPhoneNumber())
                        .build())
                .user(user)
                .recipientInfo(RecipientInfo.builder()
                        .firstName(request.getRecipientInfo().getFirstName())
                        .secondName(request.getRecipientInfo().getSecondName())
                        .patronymic(request.getRecipientInfo().getPatronymic())
                        .phoneNumber(request.getRecipientInfo().getPhoneNumber())
                        .build())
                .receivingInfo(ReceivingInfo.builder()
                        .receivingType(request.getReceivingInfo().getType())
                        .receivingDateTime(LocalDateTime.ofEpochSecond(
                                request.getReceivingInfo().getDateTimeReceivingInSeconds(),
                                0,
                                ZoneOffset.UTC))
                        .address(Address.builder()
                                .country(request.getReceivingInfo().getAddress().getCountry())
                                .state(request.getReceivingInfo().getAddress().getState())
                                .city(request.getReceivingInfo().getAddress().getCity())
                                .street(request.getReceivingInfo().getAddress().getStreet())
                                .houseNumber(request.getReceivingInfo().getAddress().getHouseNumber())
                                .flatNumber(request.getReceivingInfo().getAddress().getFlatNumber())
                                .build())
                        .build())
                .communicationMethod(communicationMethodService.getById(request.getCommunicationMethodId()))
                .paymentInfo(PaymentInfo.builder()
                        .paymentType(request.getPaymentType())
                        .finalSum(productsCost.add(deliveryCost))
                        .paymentStatus(request.getPaymentType() == PaymentType.OFFLINE_PAYMENT ?
                                PaymentStatus.REQUIRE_OFFLINE_PAYMENT :
                                PaymentStatus.REQUIRE_ONLINE_PAYMENT)
                        .build())
                .productsCost(productsCost)
                .deliveryCost(deliveryCost)
                .commentForRecipient(request.getCommentForRecipient())
                .orderComment(request.getOrderComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(OrderStatus.PROCESSING)
                .isCompleted(false)
                .build();
    }

    private OrderInfoClientResponseDto fillDto(Order order) {
        return OrderInfoClientResponseDto.builder()
                .orderId(order.getId())
                .products(order.getProducts().stream()
                        .map(product -> OrderInfoClientResponseDto.Product.builder()
                                .productId(product.getProductId())
                                .name(product.getName())
                                .count(product.getCount())
                                .price(product.getPrice())
                                .build())
                        .toList())
                .customerInfo(OrderInfoClientResponseDto.UserInfo
                        .builder()
                        .firstName(order.getCustomerInfo().getFirstName())
                        .secondName(order.getCustomerInfo().getSecondName())
                        .patronymic(order.getCustomerInfo().getPatronymic())
                        .phoneNumber(order.getCustomerInfo().getPhoneNumber())
                        .build())
                .recipientInfo(OrderInfoClientResponseDto.UserInfo.builder()
                        .firstName(order.getRecipientInfo().getFirstName())
                        .secondName(order.getRecipientInfo().getSecondName())
                        .patronymic(order.getRecipientInfo().getPatronymic())
                        .phoneNumber(order.getRecipientInfo().getPhoneNumber())
                        .build())
                .paymentInfo(OrderInfoClientResponseDto.PaymentInfo.builder()
                        .paymentType(order.getPaymentInfo().getPaymentType())
                        .finalSum(order.getPaymentInfo().getFinalSum())
                        .paymentStatus(order.getPaymentInfo().getPaymentStatus())
                        .build())
                .receivingInfo(OrderInfoClientResponseDto.ReceivingInfo.builder()
                        .receivingType(order.getReceivingInfo().getReceivingType())
                        .address(OrderInfoClientResponseDto.ReceivingInfo.AddressInfo.builder()
                                .country(order.getReceivingInfo().getAddress().getCountry())
                                .state(order.getReceivingInfo().getAddress().getState())
                                .city(order.getReceivingInfo().getAddress().getCity())
                                .street(order.getReceivingInfo().getAddress().getStreet())
                                .houseNumber(order.getReceivingInfo().getAddress().getHouseNumber())
                                .flatNumber(order.getReceivingInfo().getAddress().getFlatNumber())
                                .build())
                        .build())
                .communicationMethod(OrderInfoClientResponseDto.CommunicationMethod.builder()
                        .id(order.getCommunicationMethod().getId())
                        .name(order.getCommunicationMethod().getName())
                        .build())
                .orderStatus(order.getStatus())
                .createdAt(order.getCreatedAt())
                .isCompleted(order.isCompleted())
                .orderComment(order.getOrderComment())
                .commentForRecipient(order.getCommentForRecipient())
                .deliveryCost(order.getDeliveryCost())
                .productsCost(order.getProductsCost())
                .build();

    }
}
