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
        dto.setCreatedOn(entity.getCreatedOn());
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
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public BrandEntity toEntity(BrandEntity entity, BrandDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }
}
