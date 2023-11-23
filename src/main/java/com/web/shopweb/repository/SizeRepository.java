package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.shopweb.entity.SizeEntity;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
    SizeEntity findOneById(Long id);
}
