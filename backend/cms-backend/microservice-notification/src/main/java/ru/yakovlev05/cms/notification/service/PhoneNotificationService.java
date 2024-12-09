package ru.yakovlev05.cms.notification.service;

public interface PhoneNotificationService {
    void sendFlashcall(String text, String destination);

    void sendVoicecode(String text, String destination);

    void sendTextToSpeech(String text, String destination);
}
