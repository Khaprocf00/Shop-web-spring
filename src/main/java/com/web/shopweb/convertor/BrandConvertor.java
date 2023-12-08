package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.entity.BrandEntity;

@Component
public class BrandConvertor {
    public BrandDto toDTO(BrandEntity entity) {
        BrandDto dto = new BrandDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImagePath(entity.getImagePath());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public BrandEntity toEntity(BrandDto dto) {
        BrandEntity entity = new BrandEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        return entity;
    }

    public BrandEntity toEntity(BrandEntity entity, BrandDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        return entity;
    }
}
