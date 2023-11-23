package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.ColorDto;
import com.web.shopweb.entity.ColorEntity;

@Component
public class ColorConvertor {
    public ColorDto toDTO(ColorEntity entity) {
        ColorDto dto = new ColorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public ColorEntity toEntity(ColorDto dto) {
        ColorEntity entity = new ColorEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public ColorEntity toEntity(ColorEntity entity, ColorDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return entity;
    }
}
