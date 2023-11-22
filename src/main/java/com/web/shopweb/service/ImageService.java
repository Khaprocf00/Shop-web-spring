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

import com.web.shopweb.convertor.ImageConvertor;
import com.web.shopweb.dto.ImageDto;
import com.web.shopweb.entity.ImageEntity;
import com.web.shopweb.repository.ImageRepository;
import com.web.shopweb.storage.StorageException;
import com.web.shopweb.storage.StorageProperties;

@Service
public class ImageService {
    private Path rootLocation;

    @Autowired
    public ImageService(StorageProperties properties) {

        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getLocation() + "/image");
    }

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageConvertor imageConvertor;

    public int getTotalItem() {
        return (int) imageRepository.count();
    }

    public List<ImageDto> findAll(Pageable pageable) {
        List<ImageDto> list = new ArrayList<>();
        for (ImageEntity item : imageRepository.findAll(pageable)) {
            list.add(imageConvertor.toDTO(item));
        }
        return list;
    }

    public ImageDto findOne(Long id) {
        return imageConvertor.toDTO(imageRepository.findOneById(id));
    }

    public ImageDto save(ImageDto imageDto) {
        ImageEntity imageEntity = new ImageEntity();
        if (imageDto.getId() != null) {
            ImageEntity imageOld = imageRepository.findOneById(imageDto.getId());
            if (imageDto.getImage().getOriginalFilename() != "") {
                imageDto.setImagePath(imageDto.getImage().getOriginalFilename());
            } else {
                imageDto.setImagePath(imageOld.getImagePath());
            }
            imageEntity = imageConvertor.toEntity(imageOld, imageDto);
        }
        imageEntity = imageConvertor.toEntity(imageDto);
        if (imageDto.getImage().getOriginalFilename() != "") {
            imageEntity.setImagePath(imageDto.getImage().getOriginalFilename());
        }
        imageEntity = imageRepository.save(imageEntity);
        return imageConvertor.toDTO(imageEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            imageRepository.deleteById(id);
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
