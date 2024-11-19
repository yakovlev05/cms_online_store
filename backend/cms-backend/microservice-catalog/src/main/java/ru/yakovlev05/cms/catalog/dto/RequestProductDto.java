package ru.yakovlev05.cms.catalog.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RequestProductDto {
    private String name;

    private String description;

    private BigDecimal price;

    private List<String> categoryUrlName;

    private List<String> componentName;

    private List<String> photoName;

    private String mainPhotoName;
}
