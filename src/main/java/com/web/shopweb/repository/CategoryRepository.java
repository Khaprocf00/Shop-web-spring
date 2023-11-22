package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.shopweb.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findOneById(Long id);
}
