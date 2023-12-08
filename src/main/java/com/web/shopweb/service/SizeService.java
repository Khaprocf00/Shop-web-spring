package com.web.shopweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.web.shopweb.dto.SizeDto;

public interface SizeService {

    public int getTotalItem();

    public List<SizeDto> findAll(Pageable pageable);

    public List<SizeDto> findAll();

    public SizeDto findOne(Long id);

    public SizeDto save(SizeDto sizeDto);

    public void delete(Long[] ids);
}
