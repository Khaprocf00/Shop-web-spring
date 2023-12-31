package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.dto.ProductDto;

public interface ProductService {
    public List<ProductDto> findAll(Pageable pageable);

    public List<ProductDto> findAll();

    public ProductDto findOne(Long id);

    public ProductDto save(ProductDto productDto);

    public void delete(Long[] ids);

    public void init();

    public void deleteAll();

    public void store(MultipartFile file);

    boolean existsByName(String name);

    public int getTotalItem();
}
