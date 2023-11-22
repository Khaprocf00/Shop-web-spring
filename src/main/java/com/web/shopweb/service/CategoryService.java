package com.web.shopweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.CategoryConvertor;
import com.web.shopweb.dto.CategoryDto;
import com.web.shopweb.entity.CategoryEntity;
import com.web.shopweb.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConvertor categoryConvertor;

    public int getTotalItem() {
        return (int) categoryRepository.count();
    }

    public List<CategoryDto> findAll(Pageable pageable) {
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity item : categoryRepository.findAll(pageable)) {
            list.add(categoryConvertor.toDTO(item));
        }
        return list;
    }

    public CategoryDto findOne(Long id) {
        return categoryConvertor.toDTO(categoryRepository.findOneById(id));
    }

    public CategoryDto save(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        if (categoryDto.getId() != null) {
            CategoryEntity categoryOld = categoryRepository.findOneById(categoryDto.getId());
            categoryEntity = categoryConvertor.toEntity(categoryOld, categoryDto);
        }
        categoryEntity = categoryConvertor.toEntity(categoryDto);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvertor.toDTO(categoryEntity);
    }
}
