package com.web.shopweb.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto extends BaseDto<BrandDto> {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    private String imagePath;
    // @NotEmpty(message = "image not empty")
    private MultipartFile image;
}
