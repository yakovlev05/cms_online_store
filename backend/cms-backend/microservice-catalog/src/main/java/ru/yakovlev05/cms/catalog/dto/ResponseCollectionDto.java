package ru.yakovlev05.cms.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseCollectionDto {
    private long id;
    private MediaDto photo;
    private ResponseCategoryDto category;
}
