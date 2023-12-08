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

import com.web.shopweb.convertor.ProductConvertor;
import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.BrandRepository;
import com.web.shopweb.repository.CategoryRepository;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.ProductService;
import com.web.shopweb.storage.StorageException;
import com.web.shopweb.storage.StorageProperties;

@Service
public class ProductServiceImpl implements ProductService {
    private Path rootLocation;

    @Autowired
    public ProductServiceImpl(StorageProperties properties) {

        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation() + "/product");
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductConvertor productConvertor;

    public int getTotalItem() {
        return (int) productRepository.count();
    }

    @Override
    public List<ProductDto> findAll(Pageable pageable) {
        List<ProductDto> list = new ArrayList<>();
        for (ProductEntity item : productRepository.findAll(pageable)) {
            list.add(productConvertor.toDTO(item));

        }
        return list;
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> list = new ArrayList<>();
        for (ProductEntity item : productRepository.findAll()) {
            list.add(productConvertor.toDTO(item));
        }
        return list;
    }

    @Override
    public ProductDto findOne(Long id) {
        ProductEntity productEntity = productRepository.findOneById(id);
        return productConvertor.toDTO(productEntity);
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        ProductEntity productEntity = new ProductEntity();
        if (productDto.getId() != null) {
            ProductEntity productOld = productRepository.findOneById(productDto.getId());
            if (productDto.getImage() != null) {
                productEntity.setImagePath(productDto.getImage().getOriginalFilename());
            } else {
                productDto.setImagePath(productOld.getImagePath());
            }
            productEntity = productConvertor.toEntity(productOld, productDto);
            productEntity.setUpdatedBy(userPrincipal.getUsername());
            productEntity.setUpdatedOn(new Date());
        } else {
            productEntity = productConvertor.toEntity(productDto);
            productEntity.setCreatedBy(userPrincipal.getUsername());
            productEntity.setCreatedOn(new Date());
        }
        if (productDto.getImage() != null) {
            productEntity.setImagePath(productDto.getImage().getOriginalFilename());
        }
        if (productDto.getBrandId() != null) {
            productEntity.setBrand(brandRepository.findOneById(productDto.getBrandId()));
        }
        if (productDto.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findOneById(productDto.getCategoryId()));
        }
        productEntity = productRepository.save(productEntity);
        return productConvertor.toDTO(productEntity);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            productRepository.deleteById(id);
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
        return productRepository.existsByName(name);
    }

}
