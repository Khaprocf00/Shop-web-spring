package com.web.shopweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.SizeConvertor;
import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.dto.SizeDto;
import com.web.shopweb.entity.BrandEntity;
import com.web.shopweb.entity.SizeEntity;
import com.web.shopweb.repository.SizeRepository;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeConvertor sizeConvertor;

    public int getTotalItem() {
        return (int) sizeRepository.count();
    }

    public List<SizeDto> findAll(Pageable pageable) {
        List<SizeDto> list = new ArrayList<>();
        for (SizeEntity item : sizeRepository.findAll(pageable)) {
            list.add(sizeConvertor.toDTO(item));
        }
        return list;
    }

    public List<SizeDto> findAll() {
        List<SizeDto> list = new ArrayList<>();
        for (SizeEntity item : sizeRepository.findAll()) {
            list.add(sizeConvertor.toDTO(item));
        }
        return list;
    }

    public SizeDto findOne(Long id) {
        return sizeConvertor.toDTO(sizeRepository.findOneById(id));
    }

    public SizeDto save(SizeDto sizeDto) {
        SizeEntity sizeEntity = new SizeEntity();
        if (sizeDto.getId() != null) {
            SizeEntity sizeOld = sizeRepository.findOneById(sizeDto.getId());
            sizeEntity = sizeConvertor.toEntity(sizeOld, sizeDto);
        }
        sizeEntity = sizeConvertor.toEntity(sizeDto);
        sizeEntity = sizeRepository.save(sizeEntity);
        return sizeConvertor.toDTO(sizeEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            sizeRepository.deleteById(id);
        }
    }
}
