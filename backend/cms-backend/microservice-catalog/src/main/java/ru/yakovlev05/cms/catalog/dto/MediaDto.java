package ru.yakovlev05.cms.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MediaDto {
    private String name;
    private String url;
    private String fileName;
}
