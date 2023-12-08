package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.SliderDto;
import com.web.shopweb.entity.SliderEntity;

@Component
public class SliderConvertor {
    public SliderEntity toEntity(SliderEntity entity, SliderDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        return entity;
    }

    public SliderDto toDTO(SliderEntity entity) {
        SliderDto dto = new SliderDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImagePath(entity.getImagePath());
        dto.setContent(entity.getContent());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public SliderEntity toEntity(SliderDto dto) {
        SliderEntity entity = new SliderEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        return entity;
    }

}
