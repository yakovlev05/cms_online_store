package ru.yakovlev05.cms.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.order.entity.CommunicationMethod;
import ru.yakovlev05.cms.order.repository.CommunicationMethodRepository;
import ru.yakovlev05.cms.order.service.CommunicationMethodService;

@Service
@RequiredArgsConstructor
public class CommunicationMethodServiceImpl implements CommunicationMethodService {

    private final CommunicationMethodRepository communicationMethodRepository;

    @Override
    public CommunicationMethod getById(long communicationMethodId) {
        return communicationMethodRepository.findById(communicationMethodId)
                .orElseThrow(() -> new RuntimeException("Communication method with id " + communicationMethodId + " not found"));
    }
}
