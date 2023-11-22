package com.web.shopweb.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.shopweb.dto.CategoryDto;
import com.web.shopweb.service.CategoryService;
import com.web.shopweb.util.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setPage(page);
        categoryDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(categoryDto.getPage() - 1, categoryDto.getMaxPageItem());
        categoryDto.setTotalItem(categoryService.getTotalItem());
        categoryDto.setListResult(categoryService.findAll(pageable));
        categoryDto.setTotalPage((int) Math.ceil((double) categoryDto.getTotalItem() / categoryDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("model", categoryDto);
        return "views/category/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, CategoryDto categoryDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            categoryDto = categoryService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", categoryDto);
        return "views/category/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") CategoryDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/category/action";
        }
        item = categoryService.save(item);
        if (id != null) {
            return "redirect:/category/action?id=" + id + "&message=update_success";
        }
        return "redirect:/category/action?id=" + item.getId() + "&message=insert_success";
    }

}
