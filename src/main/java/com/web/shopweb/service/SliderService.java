package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.dto.SliderDto;

public interface SliderService {
    public List<SliderDto> findAll(Pageable pageable);

    public List<SliderDto> findAll();

    public SliderDto findOne(Long id);

    public SliderDto save(SliderDto sliderDto);

    public void delete(Long[] ids);

    public void init();

    public void deleteAll();

    public void store(MultipartFile file);

    boolean existsByName(String name);

    boolean existsByImagePath(String imagePath);

    boolean existsByContent(String content);

    public int getTotalItem();
}
