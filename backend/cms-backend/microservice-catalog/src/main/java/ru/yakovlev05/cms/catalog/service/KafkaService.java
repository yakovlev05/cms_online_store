package ru.yakovlev05.cms.catalog.service;

import ru.yakovlev05.cms.catalog.entity.Product;

public interface KafkaService {
    void sendProductCreatedEvent(Product product, boolean isAvailable);
}
