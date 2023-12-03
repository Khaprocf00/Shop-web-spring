package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.shopweb.entity.ProductEntity;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findOneById(Long id);

}
