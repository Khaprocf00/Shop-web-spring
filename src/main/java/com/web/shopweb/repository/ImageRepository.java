package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.shopweb.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findOneById(Long id);
}
