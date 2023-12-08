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

import com.web.shopweb.dto.SizeDto;
import com.web.shopweb.service.impl.SizeServiceImpl;

@RestController
@RequestMapping("/api/size")
public class SizeApi {

    @Autowired
    private SizeServiceImpl sizeService;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        SizeDto solorDto = new SizeDto();
        solorDto.setPage(page);
        solorDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(solorDto.getPage() - 1, solorDto.getMaxPageItem());
        solorDto.setTotalItem(sizeService.getTotalItem());
        solorDto.setListResult(sizeService.findAll(pageable));
        solorDto.setTotalPage((int) Math.ceil((double) solorDto.getTotalItem() / solorDto.getMaxPageItem()));
        return ResponseEntity.ok().body(solorDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SizeDto sizeDto) {
        sizeDto = sizeService.save(sizeDto);
        return ResponseEntity.ok().body(sizeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody SizeDto sizeDto, @PathVariable("id") Long id) {
        sizeDto.setId(id);
        sizeDto = sizeService.save(sizeDto);
        return ResponseEntity.ok().body(sizeDto);
    }

    @DeleteMapping
    public void delete(@RequestBody Long[] ids) {
        sizeService.delete(ids);
    }
}
