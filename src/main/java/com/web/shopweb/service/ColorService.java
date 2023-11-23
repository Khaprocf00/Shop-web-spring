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

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorConvertor colorConvertor;

    public int getTotalItem() {
        return (int) colorRepository.count();
    }

    public List<ColorDto> findAll(Pageable pageable) {
        List<ColorDto> list = new ArrayList<>();
        for (ColorEntity item : colorRepository.findAll(pageable)) {
            list.add(colorConvertor.toDTO(item));
        }
        return list;
    }

    public List<ColorDto> findAll() {
        List<ColorDto> list = new ArrayList<>();
        for (ColorEntity item : colorRepository.findAll()) {
            list.add(colorConvertor.toDTO(item));
        }
        return list;
    }

    public ColorDto findOne(Long id) {
        return colorConvertor.toDTO(colorRepository.findOneById(id));
    }

    public ColorDto save(ColorDto colorDto) {
        ColorEntity colorEntity = new ColorEntity();
        if (colorDto.getId() != null) {
            ColorEntity colorOld = colorRepository.findOneById(colorDto.getId());
            colorEntity = colorConvertor.toEntity(colorOld, colorDto);
        }
        colorEntity = colorConvertor.toEntity(colorDto);
        colorEntity = colorRepository.save(colorEntity);
        return colorConvertor.toDTO(colorEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            colorRepository.deleteById(id);
        }
    }
}
