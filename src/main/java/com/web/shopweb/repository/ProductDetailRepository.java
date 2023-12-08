package com.web.shopweb.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.shopweb.entity.ProductDetailEntity;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.entity.ColorEntity;
import com.web.shopweb.entity.SizeEntity;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
    ProductDetailEntity findOneById(Long id);

    List<ProductDetailEntity> findAllByProduct(Pageable pageable, ProductEntity productEntity);

    int countByProduct(ProductEntity productEntity);

    int countByProductAndColorAndSize(ProductEntity product, ColorEntity color, SizeEntity size);

    @Query(value = "SELECT SUM(pd.qty) FROM product_detail AS pd WHERE product_id = :productId", nativeQuery = true)
    int sumQtyByProductId(@Param("productId") Long productId);
}
