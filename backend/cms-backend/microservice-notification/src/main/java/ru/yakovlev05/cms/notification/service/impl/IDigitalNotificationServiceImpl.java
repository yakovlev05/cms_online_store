package ru.yakovlev05.cms.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yakovlev05.cms.notification.dto.IDigitalFlashcallDto;
import ru.yakovlev05.cms.notification.dto.IDigitalResponseDto;
import ru.yakovlev05.cms.notification.dto.IDigitalTextToSpeechDto;
import ru.yakovlev05.cms.notification.dto.IDigitalVoicecodeDto;
import ru.yakovlev05.cms.notification.exception.NotificationException;
import ru.yakovlev05.cms.notification.props.PhoneVerificationProps;
import ru.yakovlev05.cms.notification.service.PhoneNotificationService;


/**
 * <a href="https://i-dgtl.ru/">Сервис</a>, используемый в данной реализации
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IDigitalNotificationServiceImpl implements PhoneNotificationService {

    private final RestTemplate restTemplate;
    private final PhoneVerificationProps props;

    private static final String BASE_URL = "https://direct.i-dgtl.ru/api/v1/message";

    @Override
    public void sendFlashcall(String text, String destination) {
        IDigitalFlashcallDto data = IDigitalFlashcallDto.builder()
                .channelType("FLASHCALL")
                .senderName("FLASHCALL")
                .destination(destination)
                .content(text)
                .build();

        sendRequest(data);
    }

    @Override
    public void sendVoicecode(String text, String destination) {
        IDigitalVoicecodeDto data = IDigitalVoicecodeDto.builder()
                .channelType("VOICECODE")
                .senderName("VOICECODE")
                .destination(destination)
                .content(new IDigitalVoicecodeDto.Content("text", text))
                .build();

        sendRequest(data);
    }

    @Override
    public void sendTextToSpeech(String text, String destination) {
        IDigitalTextToSpeechDto data = IDigitalTextToSpeechDto.builder()
                .channelType("TEXT_TO_SPEECH")
                .senderName("74999553511")
                .destination(destination)
                .content(new IDigitalTextToSpeechDto.Content("female", "tts", text))
                .build();

        sendRequest(data);
    }

    private void sendRequest(Object data) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Basic " + props.getIdigitalKey());

            HttpEntity<Object> request = new HttpEntity<>(data, httpHeaders);

            ResponseEntity<IDigitalResponseDto> response = restTemplate.postForEntity(BASE_URL, request, IDigitalResponseDto.class);

            log.info("Response IDigital, flashcall: {}", response);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new NotificationException(e.getMessage());
        }
    }
}
