package com.web.shopweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.shopweb.convertor.SizeConvertor;
import com.web.shopweb.dto.SizeDto;
import com.web.shopweb.entity.SizeEntity;
import com.web.shopweb.repository.SizeRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.SizeService;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeConvertor sizeConvertor;

    @Override
    public int getTotalItem() {
        return (int) sizeRepository.count();
    }

    @Override
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

    @Override
    public SizeDto save(SizeDto sizeDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        SizeEntity sizeEntity = new SizeEntity();
        if (sizeDto.getId() != null) {
            SizeEntity sizeOld = sizeRepository.findOneById(sizeDto.getId());
            sizeEntity = sizeConvertor.toEntity(sizeOld, sizeDto);
            sizeEntity.setUpdatedBy(userPrincipal.getUsername());
            sizeEntity.setUpdatedOn(new Date());
        } else {
            sizeEntity = sizeConvertor.toEntity(sizeDto);
            sizeEntity.setCreatedBy(userPrincipal.getUsername());
            sizeEntity.setCreatedOn(new Date());
        }
        sizeEntity = sizeRepository.save(sizeEntity);
        return sizeConvertor.toDTO(sizeEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            sizeRepository.deleteById(id);
        }
    }
}
