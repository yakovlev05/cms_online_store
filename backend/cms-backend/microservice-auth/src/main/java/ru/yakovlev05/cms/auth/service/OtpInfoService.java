package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.OtpInfo;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

public interface OtpInfoService {
    OtpInfo getByChannelType(OtpChannelType channelType);
}
