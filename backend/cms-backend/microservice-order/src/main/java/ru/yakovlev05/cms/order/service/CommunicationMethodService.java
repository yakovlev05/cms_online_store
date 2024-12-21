package ru.yakovlev05.cms.order.service;

import ru.yakovlev05.cms.order.entity.CommunicationMethod;

public interface CommunicationMethodService {
    CommunicationMethod getById(long communicationMethodId);
}
