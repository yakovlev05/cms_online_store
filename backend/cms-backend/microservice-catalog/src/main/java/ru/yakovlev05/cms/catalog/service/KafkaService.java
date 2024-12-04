package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.entity.Product;
import ru.yakovlev05.cms.core.event.EventType;

public interface KafkaService {
    void sendProductEvent(Product product, boolean isAvailable, EventType eventType);
}
