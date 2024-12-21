package ru.yakovlev05.cms.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yakovlev05.cms.payment.dto.PaymentCreateDto;
import ru.yakovlev05.cms.payment.dto.PaymentResponseDto;
import ru.yakovlev05.cms.payment.entity.PaymentCurrency;
import ru.yakovlev05.cms.payment.exception.InternalServerErrorException;
import ru.yakovlev05.cms.payment.service.YookassaService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class YookassaServiceImpl implements YookassaService {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.yookassa.ru/v3";

    @Value("${yookassa.shop-id}")
    private String YOOKASSA_SHOP_ID;

    @Value("${yookassa.api-key}")
    private String YOOKASSA_API_KEY;

    @Value("${yookassa.return-url}")
    private String RETURN_URL;

    public PaymentResponseDto createPayment(String amount, String description) {
        PaymentCreateDto body = PaymentCreateDto.builder()
                .amount(PaymentCreateDto.Amount.builder()
                        .value(amount)
                        .currency(PaymentCurrency.RUB)
                        .build())
                .capture(true)
                .confirmation(PaymentCreateDto.Confirmation.builder()
                        .type("redirect")
                        .returnUrl(RETURN_URL)
                        .build())
                .description(description)
                .metadata(new PaymentCreateDto.Metadata(UUID.randomUUID().toString()))
                .build();

        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/payments")
                .build()
                .toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(YOOKASSA_SHOP_ID, YOOKASSA_API_KEY);
            headers.add("Idempotence-Key", UUID.randomUUID().toString());

            HttpEntity<PaymentCreateDto> entity = new HttpEntity<>(body, headers);

            ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, PaymentResponseDto.class);
            log.info("Response from yookassa: {}", response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to create payment", e);
            throw new InternalServerErrorException("Failed to create payment");
        }
    }
}
