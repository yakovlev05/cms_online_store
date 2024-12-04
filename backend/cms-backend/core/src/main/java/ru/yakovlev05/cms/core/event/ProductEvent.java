package ru.yakovlev05.cms.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductEvent {
    private EventType eventType;

    private long id;
    private String name;
    private String urlName;
    private String description;
    private BigDecimal price;
    private BigDecimal priceDiscount;
    private String mainPhotoUrl;
    private boolean isAvailable;
}
