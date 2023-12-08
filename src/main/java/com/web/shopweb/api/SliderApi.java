package com.web.shopweb.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import com.web.shopweb.dto.SliderDto;
import com.web.shopweb.dto.respone.ResponeMessage;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.SliderService;

@RestController
@RequestMapping("/api/slider")
public class SliderApi {
    @Autowired
    private SliderService sliderService;

    @GetMapping
    public ResponseEntity<?> show(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        SliderDto sliderDto = new SliderDto();
        sliderDto.setPage(page);
        sliderDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(sliderDto.getPage() - 1, sliderDto.getMaxPageItem());
        sliderDto.setTotalItem(sliderService.getTotalItem());
        sliderDto.setListResult(sliderService.findAll(pageable));
        sliderDto.setTotalPage((int) Math.ceil((double) sliderDto.getTotalItem() / sliderDto.getMaxPageItem()));
        return ResponseEntity.ok().body(sliderDto.getListResult());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute SliderDto sliderDto) {
        sliderDto.setImage(image);
        boolean isExsitContent = sliderService.existsByContent(sliderDto.getContent());
        if (isExsitContent) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Content is already exited")
                            .data("")
                            .build());
        }
        if (image != null) {
            boolean isExistImagePath = sliderService.existsByImagePath(sliderDto.getImage().getOriginalFilename());
            if (isExistImagePath) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        ResponeMessage.builder()
                                .status("FAILED")
                                .message("This Image Path is already exited")
                                .data("")
                                .build());
            }
        }
        boolean isExistName = sliderService.existsByName(sliderDto.getName());
        if (isExistName) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponeMessage.builder()
                            .status("FAILED")
                            .message("This Name is already exited")
                            .data("")
                            .build());
        }

        if (image != null) {
            if (image.getOriginalFilename() != "") {
                sliderService.store(sliderDto.getImage());
            }
        }
        sliderDto = sliderService.save(sliderDto);
        return ResponseEntity.ok().body(sliderDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestParam(value = "image", required = false) MultipartFile image,
            @ModelAttribute SliderDto sliderDto) {
        sliderDto.setImage(image);
        if (image != null) {
            if (image.getOriginalFilename() != "") {
                sliderService.store(sliderDto.getImage());
            }
        }
        sliderDto = sliderService.save(sliderDto);
        return ResponseEntity.ok().body(sliderDto);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        try {
            sliderService.delete(ids);
            return ResponseEntity.ok().body("DELETE SUCCESSFULLY!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("DELETE ERROR!");
        }
    }
}
