package ru.yakovlev05.cms.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ComponentRequestDto {
    private String name;
    private int count;
    private BigDecimal price;
    private boolean isInStock;
}
