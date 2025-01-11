package ru.yakovlev05.cms.cart.dto;

import lombok.Data;

@Data
public class UpdateCartDto {
    private int count;
    private boolean selected;
}
