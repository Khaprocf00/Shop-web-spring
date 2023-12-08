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
        dto.setSizeName(entity.getSize().getName());
        dto.setProductName(entity.getProduct().getName());
        dto.setColorName(entity.getColor().getName());
        dto.setQty(entity.getQty());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedOn(entity.getUpdatedOn());
        return dto;
    }

    public ProductDetailDto toDTO(ProductDetailDto dto, ProductDetailDto dtoOld) {
        dto.setId(dtoOld.getId());
        if (dto.getColorId() != null) {
            dto.setColorId(dtoOld.getColorId());
            dto.setColorName(dtoOld.getColorName());
        }
        if (dto.getProductId() != null) {
            dto.setProductName(dtoOld.getProductName());
            dto.setProductId(dtoOld.getProductId());
        }
        if (dto.getSizeId() != null) {
            dto.setSizeId(dtoOld.getSizeId());
            dto.setSizeName(dtoOld.getSizeName());
        }
        dto.setQty(dtoOld.getQty());
        dto.setCreatedBy(dtoOld.getCreatedBy());
        dto.setCreatedOn(dtoOld.getCreatedOn());
        dto.setUpdatedBy(dtoOld.getUpdatedBy());
        dto.setUpdatedOn(dtoOld.getUpdatedOn());
        return dto;
    }

    public ProductDetailEntity toEntity(ProductDetailDto dto) {
        ProductDetailEntity entity = new ProductDetailEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setQty(dto.getQty());
        return entity;
    }

    public ProductDetailEntity toEntity(ProductDetailEntity entity, ProductDetailDto dto) {
        entity.setId(dto.getId());
        entity.setQty(dto.getQty());
        return entity;
    }
}
