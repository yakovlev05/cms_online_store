package ru.yakovlev05.cms.order.entity;

public enum OrderStatus {
    PROCESSING, // в обработке (системный статус)
    PLACED, // оформлен
    PAID, // оплачен
    CONFIRMED, // подтвержден
    COLLECTING, // собирается
    IN_DELIVERY, // в доставке
    CANCELLED, // отменён
    ORDER_IS_WAITING_IN_STORE, // ожидает в магазине
    COMPLETED // выполнен
}
