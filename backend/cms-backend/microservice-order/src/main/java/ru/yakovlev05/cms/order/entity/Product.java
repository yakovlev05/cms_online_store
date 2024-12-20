package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Не соответствует данным из сервиса каталог. Просто дамп продукта на момент заказа
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "price", nullable = false, scale = 10, precision = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order order;
}
