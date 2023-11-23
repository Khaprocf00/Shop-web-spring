package com.web.shopweb.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseDto<ProductDto> {
    @NotEmpty(message = "Name is not empty")
    private String name;
    private String imagePath;
    private String[] imagePathDetail;
    private Double price;
    private Double discount;
    private int qty;
    @NotEmpty(message = "sku is not empty")
    private String sku;
    @NotEmpty(message = "content is not empty")
    private String content;
    @NotEmpty(message = "shortDescription is not empty")
    private String shortDescription;
    private MultipartFile image;
    private MultipartFile[] imageDetail;
    private Long categoryId;
    private Long brandId;
    private String categoryName;
    private String brandName;

}
