package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Синхронизировано с бд пользователей. Если пользователь авторизован и делает заказ, то эта
 * сущность цепляется к заказу.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"user\"")
public class User {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
