package ru.yakovlev05.cms.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.core.event.OrderPaymentCreateEvent;
import ru.yakovlev05.cms.payment.dto.ConfirmationUrlDto;
import ru.yakovlev05.cms.payment.dto.PaymentEventDto;
import ru.yakovlev05.cms.payment.dto.PaymentResponseDto;
import ru.yakovlev05.cms.payment.entity.Payment;
import ru.yakovlev05.cms.payment.entity.PaymentCurrency;
import ru.yakovlev05.cms.payment.entity.PaymentStatus;
import ru.yakovlev05.cms.payment.exception.BadRequestException;
import ru.yakovlev05.cms.payment.exception.InternalServerErrorException;
import ru.yakovlev05.cms.payment.exception.NotFoundException;
import ru.yakovlev05.cms.payment.repository.PaymentRepository;
import ru.yakovlev05.cms.payment.service.KafkaService;
import ru.yakovlev05.cms.payment.service.PaymentService;
import ru.yakovlev05.cms.payment.service.YookassaService;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final YookassaService yookassaService;
    private final KafkaService kafkaService;

    @Override
    public void createPayment(OrderPaymentCreateEvent request) {

        PaymentResponseDto response = yookassaService.createPayment(
                request.getAmount().toString(),
                "Заказ %s на сумма %s".formatted(request.getOrderId(), request.getAmount()));

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(PaymentCurrency.RUB)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .confirmationUrl(response.getConfirmation().getConfirmationUrl())
                .paymentId(response.getId())
                .orderId(request.getOrderId())
                .verifyKey(response.getMetadata().getVerifyKey())
                .build();

        paymentRepository.save(payment);
    }

    @Override
    public void processEvent(PaymentEventDto request) {
        log.info("Processing event {}", request);
        Payment payment = getByPaymentId(request.getObject().getId());

        switch (request.getEvent()) {
            case PAYMENT_SUCCEEDED -> handlePaymentSucceeded(request, payment);
            case PAYMENT_CANCELLED -> handlePaymentCanceled(payment);
            default -> throw new InternalServerErrorException("Can not process event " + request.getEvent());
        }

        kafkaService.sendOrderPaymentResultEvent(payment);
    }

    @Override
    public ConfirmationUrlDto getConfirmationUrl(String orderId) {
        Payment payment = getByOrderId(orderId);
        return new ConfirmationUrlDto(payment.getConfirmationUrl());
    }

    @Override
    public Payment getByPaymentId(String paymentId) {
        return paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new BadRequestException("Payment with payment_id " + paymentId + " not found"));
    }

    private Payment getByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Payment with order_id " + orderId + " not found"));
    }

    private void handlePaymentSucceeded(PaymentEventDto request, Payment payment) {
        payment.setStatus(PaymentStatus.SUCCEED);
        payment.setCapturedAt(request.getObject().getCapturedAt());

        paymentRepository.save(payment);
    }

    private void handlePaymentCanceled(Payment payment) {
        payment.setStatus(PaymentStatus.CANCELED);

        paymentRepository.save(payment);
    }
}
