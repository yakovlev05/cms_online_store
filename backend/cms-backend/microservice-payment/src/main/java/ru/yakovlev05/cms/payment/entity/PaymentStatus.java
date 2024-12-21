package ru.yakovlev05.cms.payment.entity;

public enum PaymentStatus {
    PENDING, // ожидание оплаты
    SUCCEED, // успешная оплата
    CANCELED // отмена или ошибка при оплате
}
