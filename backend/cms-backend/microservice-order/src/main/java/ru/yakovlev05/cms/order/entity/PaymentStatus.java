package ru.yakovlev05.cms.order.entity;

public enum PaymentStatus {
    REQUIRE_ONLINE_PAYMENT, // нужна онлайн оплата
    REQUIRE_OFFLINE_PAYMENT, // нужна оффлайн оплата
    PAYMENT_SUCCESS, // оплата проведена успешно
    PAYMENT_FAILED, // ошибка оплаты
}
