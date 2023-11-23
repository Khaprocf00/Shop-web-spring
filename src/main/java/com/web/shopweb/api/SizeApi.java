package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.service.SizeService;

@RestController
public class SizeApi {

    @Autowired
    private SizeService sizeService;

    @DeleteMapping("/api/size")
    public void delete(@RequestBody Long[] ids) {
        sizeService.delete(ids);
    }
}
