package ru.yakovlev05.cms.catalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "component")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "is_in_stock", nullable = false)
    private boolean isInStock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "component")
    private List<Product> products;

    public void addProduct(Product product) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(product);
    }
}
