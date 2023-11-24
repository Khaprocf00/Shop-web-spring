package com.web.shopweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.ProductDetailConvertor;
import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.entity.BrandEntity;
import com.web.shopweb.entity.ProductDetailEntity;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.ColorRepository;
import com.web.shopweb.repository.ProductDetailRepository;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.repository.SizeRepository;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductDetailConvertor productDetailConvertor;

    public int getTotalItem(ProductEntity productEntity) {
        return (int) productDetailRepository.countByProduct(productEntity);
    }

    public List<ProductDetailDto> findAll(Pageable pageable) {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAll(pageable)) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    public List<ProductDetailDto> findAllByProduct(Pageable pageable, ProductEntity productEntity) {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAllByProduct(pageable, productEntity)) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    public List<ProductDetailDto> findAll() {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAll()) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    public ProductDetailDto findOne(Long id) {
        return productDetailConvertor.toDTO(productDetailRepository.findOneById(id));
    }

    public ProductDetailDto save(ProductDetailDto productDetailDto) {
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        if (productDetailDto.getId() != null) {
            ProductDetailEntity productDetailOld = productDetailRepository.findOneById(productDetailDto.getId());
            productDetailEntity = productDetailConvertor.toEntity(productDetailOld, productDetailDto);
        }
        productDetailEntity = productDetailConvertor.toEntity(productDetailDto);
        productDetailEntity.setColor(colorRepository.findOneById(productDetailDto.getColorId()));
        productDetailEntity.setProduct(productRepository.findOneById(productDetailDto.getProductId()));
        productDetailEntity.setSize(sizeRepository.findOneById(productDetailDto.getSizeId()));
        productDetailEntity = productDetailRepository.save(productDetailEntity);
        return productDetailConvertor.toDTO(productDetailEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            productDetailRepository.deleteById(id);
        }
    }

    public int sumQtyByProductId(Long productId) {
        return productDetailRepository.sumQtyByProductId(productId);
    }
}
