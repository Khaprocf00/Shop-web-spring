package com.web.shopweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.service.ColorService;

@RestController
public class ColorApi {

    @Autowired
    private ColorService colorService;

    @DeleteMapping("/api/color")
    public void delete(@RequestBody Long[] ids) {
        colorService.delete(ids);
    }
}
