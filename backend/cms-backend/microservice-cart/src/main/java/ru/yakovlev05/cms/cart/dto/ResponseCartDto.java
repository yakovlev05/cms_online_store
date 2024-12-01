package ru.yakovlev05.cms.cart.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseCartDto {
    private long id;
    private boolean isSelected;
    private int count;
    private ProductDto product;
}
