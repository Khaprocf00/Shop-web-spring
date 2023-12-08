package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.dto.respone.ResponeMessage;
import com.web.shopweb.repository.BrandRepository;
import com.web.shopweb.repository.CategoryRepository;
import com.web.shopweb.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductApi {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        ProductDto productDto = new ProductDto();
        productDto.setPage(page);
        productDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(productDto.getPage() - 1, productDto.getMaxPageItem());
        productDto.setTotalItem(productService.getTotalItem());
        productDto.setListResult(productService.findAll(pageable));
        productDto.setTotalPage((int) Math.ceil((double) productDto.getTotalItem() / productDto.getMaxPageItem()));
        return ResponseEntity.ok().body(productDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute ProductDto productDto) {
        productDto.setImage(image);
        boolean isExistName = productService.existsByName(productDto.getName());
        if (isExistName) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Name is already exited")
                            .data("")
                            .build());
        }
        if (brandRepository.existsById(productDto.getBrandId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Brand is not already exited")
                            .data("")
                            .build());
        }
        if (categoryRepository.existsById(productDto.getCategoryId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Category is not already exited")
                            .data("")
                            .build());
        }
        if (image != null) {
            if (image.getOriginalFilename() != "") {
                productService.store(productDto.getImage());
            }
        }
        // if (productDto.getBrandId() != null) {
        // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
        // ResponeMessage.builder()
        // .status("FAILED")
        // .message("This Brand is already exited")
        // .data("")
        // .build());
        // }
        productDto = productService.save(productDto);
        return ResponseEntity.ok().body(productDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute ProductDto productDto) {
        productDto.setImage(image);
        if (image != null) {
            if (image.getOriginalFilename() != "") {
                productService.store(productDto.getImage());
            }
        }
        productDto = productService.save(productDto);
        return ResponseEntity.ok().body(productDto);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        try {
            productService.delete(ids);
            return ResponseEntity.ok().body("DELETE SUCCESSFULLY!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("DELETE ERROR!");
        }
    }
}
