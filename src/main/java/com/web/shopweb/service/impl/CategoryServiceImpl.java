package com.web.shopweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.CategoryConvertor;
import com.web.shopweb.dto.CategoryDto;
import com.web.shopweb.entity.CategoryEntity;
import com.web.shopweb.repository.CategoryRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConvertor categoryConvertor;

    @Override
    public int getTotalItem() {
        return (int) categoryRepository.count();
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity item : categoryRepository.findAll(pageable)) {
            list.add(categoryConvertor.toDTO(item));
        }
        return list;
    }

    public List<CategoryDto> findAll() {
        List<CategoryDto> list = new ArrayList<>();
        for (CategoryEntity item : categoryRepository.findAll()) {
            list.add(categoryConvertor.toDTO(item));
        }
        return list;
    }

    public CategoryDto findOne(Long id) {
        return categoryConvertor.toDTO(categoryRepository.findOneById(id));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        CategoryEntity categoryEntity = new CategoryEntity();
        if (categoryDto.getId() != null) {
            CategoryEntity categoryOld = categoryRepository.findOneById(categoryDto.getId());
            categoryEntity = categoryConvertor.toEntity(categoryOld, categoryDto);
            categoryEntity.setUpdatedBy(userPrincipal.getUsername());
            categoryEntity.setUpdatedOn(new Date());
        } else {
            categoryEntity = categoryConvertor.toEntity(categoryDto);
            categoryEntity.setCreatedBy(userPrincipal.getUsername());
            categoryEntity.setCreatedOn(new Date());
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConvertor.toDTO(categoryEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            categoryRepository.deleteById(id);
        }
    }
}
