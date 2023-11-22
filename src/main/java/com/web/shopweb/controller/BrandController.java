package com.web.shopweb.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.shopweb.dto.BrandDto;
import com.web.shopweb.service.BrandService;
import com.web.shopweb.storage.StorageFileNotFoundException;
import com.web.shopweb.util.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        BrandDto brandDto = new BrandDto();
        brandDto.setPage(page);
        brandDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(brandDto.getPage() - 1, brandDto.getMaxPageItem());
        brandDto.setTotalItem(brandService.getTotalItem());
        brandDto.setListResult(brandService.findAll(pageable));
        brandDto.setTotalPage((int) Math.ceil((double) brandDto.getTotalItem() / brandDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("model", brandDto);
        return "views/brand/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, BrandDto brandDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            brandDto = brandService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", brandDto);
        return "views/brand/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") BrandDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/brand/action";
        }
        if (item.getImage().getOriginalFilename() != "") {
            brandService.store(item.getImage());
        }
        item = brandService.save(item);
        if (id != null) {
            return "redirect:/brand/action?id=" + id + "&message=update_success";
        }
        return "redirect:/brand/action?id=" + item.getId() + "&message=insert_success";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
