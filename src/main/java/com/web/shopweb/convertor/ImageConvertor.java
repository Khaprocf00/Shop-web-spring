package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.ImageDto;
import com.web.shopweb.entity.ImageEntity;

@Component
public class ImageConvertor {
    public ImageDto toDTO(ImageEntity entity) {
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImagePath(entity.getImagePath());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public ImageEntity toEntity(ImageDto dto) {
        ImageEntity entity = new ImageEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public ImageEntity toEntity(ImageEntity entity, ImageDto dto) {
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
