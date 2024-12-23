package ru.yakovlev05.cms.catalog.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class ResponseProductDto {
    private String name;

    private String urlName;

    private String description;

    private BigDecimal price;

    private BigDecimal priceDiscount;

    private List<ComponentRequestDto> components;

    private List<ResponseCategoryDto> categories;

    private List<String> photoUrls;

    private String mainPhotoUrl;
}
