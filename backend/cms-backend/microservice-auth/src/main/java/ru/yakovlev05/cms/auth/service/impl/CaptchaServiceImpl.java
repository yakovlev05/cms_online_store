package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.yakovlev05.cms.auth.dto.YandexCaptchaDto;
import ru.yakovlev05.cms.auth.exception.InternalServerErrorException;
import ru.yakovlev05.cms.auth.service.CaptchaService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final RestTemplate restTemplate;

    private static final String CAPTCHA_URL = "https://smartcaptcha.yandexcloud.net/validate";
    private static final String SUCCESS_CAPTCHA_STATUS = "ok";

    @Value("${captchaYandex.serverKey}")
    private String serverKey;


    @Override
    public boolean verify(String token, String ip) {
        String url = UriComponentsBuilder.fromHttpUrl(CAPTCHA_URL)
                .queryParam("secret", serverKey)
                .queryParam("token", token)
                .queryParam("ip", ip)
                .build()
                .toString();

        log.info("Try validate captcha, token: {}", token);

        try {
            YandexCaptchaDto response = restTemplate.postForObject(url, null, YandexCaptchaDto.class);

            if (response != null && response.getStatus().equals(SUCCESS_CAPTCHA_STATUS)) {
                log.info("Captcha is valid");
                return true;
            }

        } catch (Exception e) {
            throw new InternalServerErrorException("Error validate yandex captcha");
        }

        log.info("Captcha is invalid");
        return false;
    }
}
