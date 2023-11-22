package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.service.BrandService;

@RestController
public class BrandApi {
    @Autowired
    private BrandService brandService;

    @DeleteMapping("/api/brand")
    public void delete(@RequestBody Long[] ids) {
        brandService.delete(ids);
    }
}
