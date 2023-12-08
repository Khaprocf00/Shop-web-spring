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

import com.web.shopweb.dto.ColorDto;
import com.web.shopweb.service.ColorService;

@RestController
@RequestMapping("/api/color")
public class ColorApi {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        ColorDto solorDto = new ColorDto();
        solorDto.setPage(page);
        solorDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(solorDto.getPage() - 1, solorDto.getMaxPageItem());
        solorDto.setTotalItem(colorService.getTotalItem());
        solorDto.setListResult(colorService.findAll(pageable));
        solorDto.setTotalPage((int) Math.ceil((double) solorDto.getTotalItem() / solorDto.getMaxPageItem()));
        return ResponseEntity.ok().body(solorDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ColorDto colorDto) {
        colorDto = colorService.save(colorDto);
        return ResponseEntity.ok().body(colorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ColorDto colorDto, @PathVariable("id") Long id) {
        colorDto.setId(id);
        colorDto = colorService.save(colorDto);
        return ResponseEntity.ok().body(colorDto);
    }

    @DeleteMapping
    public void delete(@RequestBody Long[] ids) {
        colorService.delete(ids);
    }
}
