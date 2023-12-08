package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.dto.CategoryDto;
import com.web.shopweb.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        CategoryDto solorDto = new CategoryDto();
        solorDto.setPage(page);
        solorDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(solorDto.getPage() - 1, solorDto.getMaxPageItem());
        solorDto.setTotalItem(categoryService.getTotalItem());
        solorDto.setListResult(categoryService.findAll(pageable));
        solorDto.setTotalPage((int) Math.ceil((double) solorDto.getTotalItem() / solorDto.getMaxPageItem()));
        return ResponseEntity.ok().body(solorDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto) {
        categoryDto = categoryService.save(categoryDto);
        return ResponseEntity.ok().body(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long id) {
        categoryDto.setId(id);
        categoryDto = categoryService.save(categoryDto);
        return ResponseEntity.ok().body(categoryDto);
    }

    @DeleteMapping
    public void delete(@RequestBody Long[] ids) {
        categoryService.delete(ids);
    }
}
