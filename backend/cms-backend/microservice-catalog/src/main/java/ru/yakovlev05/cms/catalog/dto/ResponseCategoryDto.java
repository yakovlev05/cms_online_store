package ru.yakovlev05.cms.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseCategoryDto {

    private String name;

    private String urlName;
}
