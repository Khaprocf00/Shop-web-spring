package com.web.shopweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.shopweb.entity.SliderEntity;
import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<SliderEntity, Long> {
    boolean existsByName(String name);

    boolean existsByImagePath(String imagePath);

    boolean existsByContent(String content);

    SliderEntity findOneById(Long id);
}
