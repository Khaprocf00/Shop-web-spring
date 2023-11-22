package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.service.CategoryService;

@RestController
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @DeleteMapping("/api/category")
    public void delete( @RequestBody Long[] ids) {
        categoryService.delete(ids);
    }
}
