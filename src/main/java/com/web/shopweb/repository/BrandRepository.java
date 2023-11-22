package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.shopweb.entity.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findOneById(Long id);
}
