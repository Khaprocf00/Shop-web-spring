package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.service.ImageService;

@RestController
public class ImageApi {
    @Autowired
    private ImageService imageService;

    @DeleteMapping("/api/image")
    public void delete(@RequestBody Long[] ids) {
        imageService.delete(ids);
    }
}
