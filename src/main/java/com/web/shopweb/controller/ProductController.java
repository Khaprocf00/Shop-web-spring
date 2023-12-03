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

import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.service.BrandService;
import com.web.shopweb.service.CategoryService;
import com.web.shopweb.service.ProductService;
import com.web.shopweb.storage.StorageFileNotFoundException;
import com.web.shopweb.util.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        ProductDto productDto = new ProductDto();
        productDto.setPage(page);
        productDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(productDto.getPage() - 1, productDto.getMaxPageItem());
        productDto.setTotalItem(productService.getTotalItem());
        productDto.setListResult(productService.findAll(pageable));
        productDto.setTotalPage((int) Math.ceil((double) productDto.getTotalItem() / productDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }

        model.addAttribute("model", productDto);
        return "views/product/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, ProductDto productDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            productDto = productService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", productDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "views/product/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") ProductDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "views/product/action";
        }
        if (item.getImage().getOriginalFilename() != "") {
            productService.store(item.getImage());
        }
        item = productService.save(item);
        if (id != null) {
            return "redirect:/product/action?id=" + id + "&message=update_success";
        }
        return "redirect:/product/action?id=" + item.getId() + "&message=insert_success";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
