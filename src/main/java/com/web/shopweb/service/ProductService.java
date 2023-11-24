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

import com.web.shopweb.convertor.ProductConvertor;
import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.BrandRepository;
import com.web.shopweb.repository.CategoryRepository;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.storage.StorageException;
import com.web.shopweb.storage.StorageProperties;

@Service
public class ProductService {
    private Path rootLocation;

    @Autowired
    public ProductService(StorageProperties properties) {

        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation() + "/product");
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductConvertor productConvertor;

    public int getTotalItem() {
        return (int) productRepository.count();
    }

    public List<ProductDto> findAll(Pageable pageable) {
        List<ProductDto> list = new ArrayList<>();
        for (ProductEntity item : productRepository.findAll(pageable)) {
            list.add(productConvertor.toDTO(item));
        }
        return list;
    }

    public ProductDto findOne(Long id) {
        return productConvertor.toDTO(productRepository.findOneById(id));
    }

    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        if (productDto.getId() != null) {
            ProductEntity productOld = productRepository.findOneById(productDto.getId());
            if (productDto.getImage() != null) {
                if (productDto.getImage().getOriginalFilename() != "") {
                    productDto.setImagePath(productDto.getImage().getOriginalFilename());
                }
            } else {
                productDto.setImagePath(productOld.getImagePath());
            }
            productEntity = productConvertor.toEntity(productOld, productDto);
        }
        productEntity = productConvertor.toEntity(productDto);
        if (productDto.getImage() != null) {
            if (productDto.getImage().getOriginalFilename() != "") {
                productEntity.setImagePath(productDto.getImage().getOriginalFilename());
            }
        }
        productEntity.setBrand(brandRepository.findOneById(productDto.getBrandId()));
        productEntity.setCategory(categoryRepository.findOneById(productDto.getCategoryId()));
        productEntity = productRepository.save(productEntity);
        return productConvertor.toDTO(productEntity);
    }

    public void delete(Long[] ids) {
        for (Long id : ids) {
            productRepository.deleteById(id);
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
