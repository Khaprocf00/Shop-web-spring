package com.web.shopweb.convertor;

import org.springframework.stereotype.Component;

import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.entity.ProductEntity;

@Component
public class ProductConvertor {
    public ProductDto toDTO(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImagePath(entity.getImagePath());
        dto.setPrice(entity.getPrice());
        dto.setDiscount(entity.getDiscount());
        dto.setContent(entity.getContent());
        dto.setShortDescription(entity.getShortDescription());
        dto.setSku(entity.getSku());
        dto.setQty(entity.getQty());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setBrandId(entity.getBrand().getId());
        dto.setCategoryName(entity.getCategory().getName());
        dto.setBrandName(entity.getBrand().getName());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        entity.setContent(dto.getContent());
        entity.setShortDescription(dto.getShortDescription());
        entity.setSku(dto.getSku());
        entity.setQty(dto.getQty());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }

    public ProductEntity toEntity(ProductEntity entity, ProductDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImagePath(dto.getImagePath());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        entity.setContent(dto.getContent());
        entity.setShortDescription(dto.getShortDescription());
        entity.setSku(dto.getSku());
        entity.setQty(dto.getQty());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setUpdatedOn(dto.getUpdatedOn());
        return entity;
    }
}
