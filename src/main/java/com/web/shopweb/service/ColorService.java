package com.web.shopweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.ColorConvertor;
import com.web.shopweb.dto.ColorDto;
import com.web.shopweb.entity.ColorEntity;
import com.web.shopweb.repository.ColorRepository;

public interface ColorService {

    public int getTotalItem();

    public List<ColorDto> findAll(Pageable pageable);

    public List<ColorDto> findAll();

    public ColorDto findOne(Long id);

    public ColorDto save(ColorDto colorDto);

    public void delete(Long[] ids);
}
