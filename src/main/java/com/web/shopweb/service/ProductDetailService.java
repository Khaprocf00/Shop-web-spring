package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.entity.ColorEntity;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.entity.SizeEntity;

public interface ProductDetailService {

    public int getTotalItem(ProductEntity productEntity);

    public List<ProductDetailDto> findAll(Pageable pageable);

    public List<ProductDetailDto> findAllByProduct(Pageable pageable, ProductEntity productEntity);

    public List<ProductDetailDto> findAll();

    public ProductDetailDto findOne(Long id);

    public void delete(Long[] ids);

    public int sumQtyByProductId(Long productId);

    public ProductDetailDto save(ProductDetailDto productDetailDto);

    public int countByProductAndColorAndSize(ProductDetailDto productDetailDto);
}
