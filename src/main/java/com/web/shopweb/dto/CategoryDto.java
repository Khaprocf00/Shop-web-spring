package com.web.shopweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseDto<CategoryDto> {

    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "Code is mandatory")
    private String code;
}
