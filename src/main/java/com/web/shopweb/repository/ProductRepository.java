package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.shopweb.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findOneById(Long id);

}
