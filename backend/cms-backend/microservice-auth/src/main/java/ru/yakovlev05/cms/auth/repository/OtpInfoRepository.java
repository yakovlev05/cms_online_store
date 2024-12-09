package ru.yakovlev05.cms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.auth.entity.OtpInfo;
import ru.yakovlev05.cms.core.entity.OtpChannelType;

import java.util.Optional;

public interface OtpInfoRepository extends JpaRepository<OtpInfo, Long> {
    Optional<OtpInfo> findByChannelType(OtpChannelType channelType);
}
