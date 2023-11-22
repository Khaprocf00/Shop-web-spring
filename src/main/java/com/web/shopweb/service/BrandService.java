package com.web.shopweb.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.convertor.BrandConvertor;
import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.entity.BrandEntity;
import com.web.shopweb.repository.BrandRepository;
import com.web.shopweb.storage.StorageException;
import com.web.shopweb.storage.StorageProperties;

@Service
public class BrandService {
    private Path rootLocation;

    @Autowired
    public BrandService(StorageProperties properties) {

        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation() + "/brand");
    }

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandConvertor brandConvertor;

    public int getTotalItem() {
        return (int) brandRepository.count();
    }

    public List<BrandDto> findAll(Pageable pageable) {
        List<BrandDto> list = new ArrayList<>();
        for (BrandEntity item : brandRepository.findAll(pageable)) {
            list.add(brandConvertor.toDTO(item));
        }
        return list;
    }

    public BrandDto findOne(Long id) {
        return brandConvertor.toDTO(brandRepository.findOneById(id));
    }

    public BrandDto save(BrandDto brandDto) {
        BrandEntity brandEntity = new BrandEntity();
        if (brandDto.getId() != null) {
            BrandEntity brandOld = brandRepository.findOneById(brandDto.getId());
            if (brandDto.getImage().getOriginalFilename() != "") {
                brandDto.setImagePath(brandDto.getImage().getOriginalFilename());
            } else {
                brandDto.setImagePath(brandOld.getImagePath());
            }
            brandEntity = brandConvertor.toEntity(brandOld, brandDto);
        }
        brandEntity = brandConvertor.toEntity(brandDto);
        if (brandDto.getImage().getOriginalFilename() != "") {
            brandEntity.setImagePath(brandDto.getImage().getOriginalFilename());
        }
        brandEntity = brandRepository.save(brandEntity);
        return brandConvertor.toDTO(brandEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandRepository.deleteById(id);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public void store(MultipartFile file) {
        try {
            // if (file.isEmpty()) {
            // throw new StorageException("Failed to store empty file.");
            // }
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
}
