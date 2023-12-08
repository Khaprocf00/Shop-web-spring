package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.web.shopweb.dto.CategoryDto;

public interface CategoryService {

    public int getTotalItem();

    public List<CategoryDto> findAll(Pageable pageable);

    public List<CategoryDto> findAll();

    public CategoryDto findOne(Long id);

    public CategoryDto save(CategoryDto categoryDto);

    public void delete(Long[] ids);
}
