package ru.yakovlev05.cms.catalog.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RequestProductDto {
    private String name;

    private String description;

    private BigDecimal price;

    private List<String> categoriesUrlsNames;

    private List<String> componentsNames;

    private List<String> photosFileNames;

    private String mainPhotoFileName;
}
