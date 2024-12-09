package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.OtpInfo;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.repository.OtpInfoRepository;
import ru.yakovlev05.cms.auth.service.OtpInfoService;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

@RequiredArgsConstructor
@Service
public class OtpInfoServiceImpl implements OtpInfoService {

    private final OtpInfoRepository otpInfoRepository;

    @Override
    public OtpInfo getByChannelType(OtpChannelType channelType) {
        return otpInfoRepository.findByChannelType(channelType)
                .orElseThrow(() -> new BadRequestException("Otp channel type not found: " + channelType));
    }
}
