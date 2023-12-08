package com.web.shopweb.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.service.BrandService;

@RestController
@RequestMapping("/api/brand")
public class BrandApi {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        BrandDto brandDto = new BrandDto();
        brandDto.setPage(page);
        brandDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(brandDto.getPage() - 1, brandDto.getMaxPageItem());
        brandDto.setTotalItem(brandService.getTotalItem());
        brandDto.setListResult(brandService.findAll(pageable));
        brandDto.setTotalPage((int) Math.ceil((double) brandDto.getTotalItem() / brandDto.getMaxPageItem()));
        return ResponseEntity.ok().body(brandDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute BrandDto brandDto) {
        brandDto.setImage(image);
        if (image != null) {
            brandService.store(brandDto.getImage());
        }
        brandDto = brandService.save(brandDto);
        return ResponseEntity.ok().body(brandDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute BrandDto brandDto) {
        brandDto.setImage(image);
        if (image != null) {
            brandService.store(brandDto.getImage());
        }
        brandDto = brandService.save(brandDto);
        return ResponseEntity.ok().body(brandDto);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        try {
            brandService.delete(ids);
            return ResponseEntity.ok().body("DELETE SUCCESSFULLY!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("DELETE ERROR!");
        }
    }
}
