package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о заказчике. То есть можно подписаться кастомно.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer_info")
public class CustomerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;
}
