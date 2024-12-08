package ru.yakovlev05.cms.auth.service;

public interface CaptchaService {

    boolean verify(String token, String ip);

}
