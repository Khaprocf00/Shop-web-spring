package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.shopweb.entity.ColorEntity;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    ColorEntity findOneById(Long id);

    boolean existsById(Long id);
}
