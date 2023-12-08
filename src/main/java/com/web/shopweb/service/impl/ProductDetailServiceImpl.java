package com.web.shopweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.ProductDetailConvertor;
import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.entity.ColorEntity;
import com.web.shopweb.entity.ProductDetailEntity;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.ColorRepository;
import com.web.shopweb.repository.ProductDetailRepository;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.repository.SizeRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

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

    @Override
    public int getTotalItem(ProductEntity productEntity) {
        return (int) productDetailRepository.countByProduct(productEntity);
    }

    @Override
    public List<ProductDetailDto> findAll(Pageable pageable) {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAll(pageable)) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    @Override
    public List<ProductDetailDto> findAllByProduct(Pageable pageable, ProductEntity productEntity) {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAllByProduct(pageable, productEntity)) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    @Override
    public List<ProductDetailDto> findAll() {
        List<ProductDetailDto> list = new ArrayList<>();
        for (ProductDetailEntity item : productDetailRepository.findAll()) {
            list.add(productDetailConvertor.toDTO(item));
        }
        return list;
    }

    @Override
    public ProductDetailDto findOne(Long id) {
        return productDetailConvertor.toDTO(productDetailRepository.findOneById(id));
    }

    @Override
    public ProductDetailDto save(ProductDetailDto productDetailDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        ProductDetailEntity productDetailEntity = new ProductDetailEntity();
        if (productDetailDto.getId() != null) {
            ProductDetailEntity productDetailOld = productDetailRepository.findOneById(productDetailDto.getId());
            productDetailEntity = productDetailConvertor.toEntity(productDetailOld, productDetailDto);
            productDetailEntity.setUpdatedBy(userPrincipal.getUsername());
            productDetailEntity.setUpdatedOn(new Date());
        } else {
            productDetailEntity = productDetailConvertor.toEntity(productDetailDto);
            productDetailEntity.setCreatedBy(userPrincipal.getUsername());
            productDetailEntity.setCreatedOn(new Date());
        }
        productDetailEntity.setColor(colorRepository.findOneById(productDetailDto.getColorId()));
        productDetailEntity.setProduct(productRepository.findOneById(productDetailDto.getProductId()));
        productDetailEntity.setSize(sizeRepository.findOneById(productDetailDto.getSizeId()));
        productDetailEntity = productDetailRepository.save(productDetailEntity);
        return productDetailConvertor.toDTO(productDetailEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            productDetailRepository.deleteById(id);
        }
    }

    @Override
    public int sumQtyByProductId(Long productId) {
        return productDetailRepository.sumQtyByProductId(productId);
    }

    @Override
    public int countByProductAndColorAndSize(ProductDetailDto productDetailDto) {
        return productDetailRepository.countByProductAndColorAndSize(
                productRepository.findOneById(productDetailDto.getProductId()),
                colorRepository.findOneById(productDetailDto.getColorId()),
                sizeRepository.findOneById(productDetailDto.getSizeId()));
    }
}
