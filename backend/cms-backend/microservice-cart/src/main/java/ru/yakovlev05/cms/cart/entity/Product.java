package ru.yakovlev05.cms.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    public long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url_name", nullable = false, unique = true)
    private String urlName;

    @Column(name = "main_photo_url", nullable = false)
    private String mainPhotoUrl;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "price_discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;
}
