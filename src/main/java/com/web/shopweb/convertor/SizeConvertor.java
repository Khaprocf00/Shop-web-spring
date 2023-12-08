package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.SizeDto;
import com.web.shopweb.entity.SizeEntity;

@Component
public class SizeConvertor {
    public SizeDto toDTO(SizeEntity entity) {
        SizeDto dto = new SizeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public SizeEntity toEntity(SizeDto dto) {
        SizeEntity entity = new SizeEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        return entity;
    }

    public SizeEntity toEntity(SizeEntity entity, SizeDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
