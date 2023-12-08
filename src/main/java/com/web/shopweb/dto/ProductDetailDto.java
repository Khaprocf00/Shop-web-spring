package com.web.shopweb.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto extends BaseDto<ProductDetailDto> {

    @NotNull(message = "Size is not empty")
    private Long sizeId;
    private Long productId;
    @NotNull(message = "Color is not empty")
    private Long colorId;
    // @NotEmpty(message = "Qty is not empty")
    private int qty;
    private String sizeName;
    private String colorName;
    private String productName;
}
