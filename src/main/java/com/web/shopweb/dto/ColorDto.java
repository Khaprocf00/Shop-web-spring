package com.web.shopweb.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto extends BaseDto<ColorDto> {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "Code is mandatory")
    private String code;
}
