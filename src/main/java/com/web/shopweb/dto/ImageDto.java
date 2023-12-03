package com.web.shopweb.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends BaseDto<ImageDto> {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    private String imagePath;
    // @NotEmpty(message = "image not empty")
    private MultipartFile image;
}
