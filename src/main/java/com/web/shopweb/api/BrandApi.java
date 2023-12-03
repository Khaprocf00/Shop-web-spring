package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.service.BrandService;

@RestController
@RequestMapping("/api")
public class BrandApi {
    @Autowired
    private BrandService brandService;

    @GetMapping("/brand")
    public ResponseEntity<?> show() {
        BrandDto brandDto = new BrandDto();
        brandDto.setListResult(brandService.findAll());
        return ResponseEntity.ok().body(BrandDto.builder()
                .listResult(brandService.findAll())
                .build());
    }

    @DeleteMapping("/brand")
    public void delete(@RequestBody Long[] ids) {
        brandService.delete(ids);
    }
}
