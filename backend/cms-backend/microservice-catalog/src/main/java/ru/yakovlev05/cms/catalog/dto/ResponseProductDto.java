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

    private List<ComponentDto> component;

    private List<String> categoryName;

    private List<String> photoName;

    private String mainPhotoName;
}
