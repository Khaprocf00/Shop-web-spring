package com.web.shopweb.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.convertor.SliderConvertor;
import com.web.shopweb.dto.SliderDto;
import com.web.shopweb.entity.SliderEntity;
import com.web.shopweb.repository.SliderRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.SliderService;
import com.web.shopweb.storage.StorageException;
import com.web.shopweb.storage.StorageProperties;

@Service
public class SliderServiceImpl implements SliderService {
    private Path rootLocation;

    @Autowired
    public SliderServiceImpl(StorageProperties properties) {

        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation() + "/slider");
    }

    @Autowired
    private SliderRepository sliderRepository;
    @Autowired
    private SliderConvertor sliderConvertor;

    public int getTotalItem() {
        return (int) sliderRepository.count();
    }

    @Override
    public List<SliderDto> findAll(Pageable pageable) {
        List<SliderDto> list = new ArrayList<>();
        for (SliderEntity item : sliderRepository.findAll(pageable)) {
            list.add(sliderConvertor.toDTO(item));

        }
        return list;
    }

    @Override
    public List<SliderDto> findAll() {
        List<SliderDto> list = new ArrayList<>();
        for (SliderEntity item : sliderRepository.findAll()) {
            list.add(sliderConvertor.toDTO(item));
        }
        return list;
    }

    @Override
    public SliderDto findOne(Long id) {
        SliderEntity sliderEntity = sliderRepository.findOneById(id);
        return sliderConvertor.toDTO(sliderEntity);
    }

    @Override
    public SliderDto save(SliderDto sliderDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        SliderEntity sliderEntity = new SliderEntity();
        if (sliderDto.getId() != null) {
            SliderEntity sliderOld = sliderRepository.findOneById(sliderDto.getId());
            if (sliderDto.getImage() != null) {
                sliderEntity.setImagePath(sliderDto.getImage().getOriginalFilename());
            } else {
                sliderDto.setImagePath(sliderOld.getImagePath());
            }
            sliderEntity = sliderConvertor.toEntity(sliderOld, sliderDto);
            sliderEntity.setUpdatedBy(userPrincipal.getUsername());
            sliderEntity.setUpdatedOn(new Date());
        } else {
            sliderEntity = sliderConvertor.toEntity(sliderDto);
            sliderEntity.setCreatedBy(userPrincipal.getUsername());
            sliderEntity.setCreatedOn(new Date());
        }
        if (sliderDto.getImage() != null) {
            sliderEntity.setImagePath(sliderDto.getImage().getOriginalFilename());
        }
        sliderEntity = sliderRepository.save(sliderEntity);
        return sliderConvertor.toDTO(sliderEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            sliderRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return sliderRepository.existsByName(name);
    }

    @Override
    public boolean existsByImagePath(String imagePath) {
        return sliderRepository.existsByImagePath(imagePath);
    }

    @Override
    public boolean existsByContent(String content) {
        return sliderRepository.existsByContent(content);
    }
}
