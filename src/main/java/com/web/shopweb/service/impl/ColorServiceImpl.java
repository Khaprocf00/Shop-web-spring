package com.web.shopweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.ColorConvertor;
import com.web.shopweb.dto.ColorDto;
import com.web.shopweb.entity.ColorEntity;
import com.web.shopweb.repository.ColorRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorConvertor colorConvertor;

    @Override
    public int getTotalItem() {
        return (int) colorRepository.count();
    }

    @Override
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

    @Override
    public ColorDto save(ColorDto colorDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        ColorEntity colorEntity = new ColorEntity();
        if (colorDto.getId() != null) {
            ColorEntity colorOld = colorRepository.findOneById(colorDto.getId());
            colorEntity = colorConvertor.toEntity(colorOld, colorDto);
            colorEntity.setUpdatedBy(userPrincipal.getUsername());
            colorEntity.setUpdatedOn(new Date());
        } else {
            colorEntity = colorConvertor.toEntity(colorDto);
            colorEntity.setCreatedBy(userPrincipal.getUsername());
            colorEntity.setCreatedOn(new Date());
        }
        colorEntity = colorRepository.save(colorEntity);
        return colorConvertor.toDTO(colorEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            colorRepository.deleteById(id);
        }
    }
}
