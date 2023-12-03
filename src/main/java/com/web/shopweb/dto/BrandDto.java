package com.web.shopweb.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BrandDto extends BaseDto<BrandDto> {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    private String imagePath;
    // @NotEmpty(message = "image not empty")
    private MultipartFile image;
}
