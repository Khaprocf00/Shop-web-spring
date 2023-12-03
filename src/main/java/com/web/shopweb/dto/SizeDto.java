package com.web.shopweb.dto;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeDto extends BaseDto<SizeDto> {
    @NotEmpty(message = "Name is mandatory")
    private String name;
}
