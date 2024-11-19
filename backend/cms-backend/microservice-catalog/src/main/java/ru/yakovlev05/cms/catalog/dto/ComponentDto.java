package ru.yakovlev05.cms.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ComponentDto {
    private String name;
    private int count;
}
