package com.web.shopweb.api;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.dto.SliderDto;
import com.web.shopweb.dto.respone.ResponeMessage;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.ColorRepository;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.repository.SizeRepository;
import com.web.shopweb.service.ColorService;
import com.web.shopweb.service.ProductDetailService;
import com.web.shopweb.service.ProductService;
import com.web.shopweb.service.impl.SizeServiceImpl;

@RestController
@RequestMapping("/api/productDetail/{productId}")
public class ProductDetailApi {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit,
            @PathVariable(name = "productId") Long productId) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        ProductEntity productEntity = new ProductEntity();
        productEntity = productRepository.findOneById(productId);
        productDetailDto.setPage(page);
        productDetailDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(productDetailDto.getPage() - 1, productDetailDto.getMaxPageItem());
        productDetailDto.setTotalItem(productDetailService.getTotalItem(productEntity));
        productDetailDto.setListResult(productDetailService.findAllByProduct(pageable, productEntity));
        productDetailDto.setTotalPage(
                (int) Math.ceil((double) productDetailDto.getTotalItem() / productDetailDto.getMaxPageItem()));
        return ResponseEntity.ok().body(productDetailDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDetailDto productDetailDto,
            @RequestParam(value = "id", required = false) Long id,
            @PathVariable(name = "productId") Long productId)
            throws IOException {
        productDetailDto.setProductId(productId);
        if (productRepository.existsById(productDetailDto.getProductId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Product is already exited")
                            .data("")
                            .build());
        }
        if (colorRepository.existsById(productDetailDto.getColorId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Color is already exited")
                            .data("")
                            .build());
        }
        if (sizeRepository.existsById(productDetailDto.getSizeId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Size is already exited")
                            .data("")
                            .build());
        }
        if (productDetailService.countByProductAndColorAndSize(productDetailDto) != 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Product Detail is already exited")
                            .data("")
                            .build());
        }
        productDetailDto = productDetailService.save(productDetailDto);
        ProductDto productDto = productService.findOne(productId);
        productDto.setQty(productDetailService.sumQtyByProductId(productId));
        productDto = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponeMessage.builder()
                        .status("SUCCESS")
                        .message("Create Successfully!!!")
                        .data(productDetailDto)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ProductDetailDto productDetailDto,
            @PathVariable(name = "id") Long id,
            @PathVariable(name = "productId") Long productId)
            throws IOException {
        productDetailDto = productDetailService.findOne(id);
        if (productRepository.existsById(productDetailDto.getProductId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Product is already exited")
                            .data("")
                            .build());
        }
        if (colorRepository.existsById(productDetailDto.getColorId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Color is already exited")
                            .data("")
                            .build());
        }
        if (sizeRepository.existsById(productDetailDto.getSizeId()) == false) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Size is already exited")
                            .data("")
                            .build());
        }
        productDetailDto = productDetailService.save(productDetailDto);
        ProductDto productDto = productService.findOne(productId);
        productDto.setQty(productDetailService.sumQtyByProductId(productId));
        productDto = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponeMessage.builder()
                        .status("SUCCESS")
                        .message("Update Successfully!!!")
                        .data(productDetailDto)
                        .build());
    }
}
