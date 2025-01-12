package ru.yakovlev05.cms.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductDto {
    private long id;
    private String name;
    private String urlName;
    private String mainPhotoUrl;
    private BigDecimal price;
    private BigDecimal discount;
    private boolean isAvailable;
}
