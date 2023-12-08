package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.dto.BrandDto;

public interface BrandService {
    public List<BrandDto> findAll(Pageable pageable);

    public List<BrandDto> findAll();

    public BrandDto findOne(Long id);

    public BrandDto save(BrandDto brandDto);

    public void delete(Long[] ids);

    public void init();

    public void deleteAll();

    public void store(MultipartFile file);

    boolean existsByName(String name);

    public int getTotalItem();
}
