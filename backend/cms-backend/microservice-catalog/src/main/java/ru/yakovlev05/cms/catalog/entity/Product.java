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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url_name", nullable = false, unique = true)
    private String urlName;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "price_discount", precision = 10, scale = 2)
    private BigDecimal priceDiscount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    )
    private List<Category> categories = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_media",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "media_id", referencedColumnName = "id", nullable = false)
    )
    private List<Media> media = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_component",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id", nullable = false)
    )
    private List<Component> components = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "main_photo", referencedColumnName = "id")
    private Media mainPhoto;
}
