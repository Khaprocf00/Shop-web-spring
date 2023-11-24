package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.entity.ProductDetailEntity;

@Component
public class ProductDetailConvertor {
    public ProductDetailDto toDTO(ProductDetailEntity entity) {
        ProductDetailDto dto = new ProductDetailDto();
        dto.setId(entity.getId());
        dto.setColorId(entity.getColor().getId());
        dto.setSizeId(entity.getSize().getId());
        dto.setProductId(entity.getProduct().getId());
        dto.setQty(entity.getQty());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public ProductDetailEntity toEntity(ProductDetailDto dto) {
        ProductDetailEntity entity = new ProductDetailEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setQty(dto.getQty());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public ProductDetailEntity toEntity(ProductDetailEntity entity, ProductDetailDto dto) {
        entity.setId(dto.getId());
        entity.setQty(dto.getQty());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return entity;
    }
}
