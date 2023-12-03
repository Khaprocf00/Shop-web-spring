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

import com.web.shopweb.dto.ImageDto;
import com.web.shopweb.service.ImageService;
import com.web.shopweb.storage.StorageFileNotFoundException;
import com.web.shopweb.util.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        ImageDto imageDto = new ImageDto();
        imageDto.setPage(page);
        imageDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(imageDto.getPage() - 1, imageDto.getMaxPageItem());
        imageDto.setTotalItem(imageService.getTotalItem());
        imageDto.setListResult(imageService.findAll(pageable));
        imageDto.setTotalPage((int) Math.ceil((double) imageDto.getTotalItem() / imageDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("model", imageDto);
        return "views/image/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, ImageDto imageDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            imageDto = imageService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", imageDto);
        return "views/image/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") ImageDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/image/action";
        }
        if (item.getImage().getOriginalFilename() != "") {
            imageService.store(item.getImage());
        }
        item = imageService.save(item);
        if (id != null) {
            return "redirect:/image/action?id=" + id + "&message=update_success";
        }
        return "redirect:/image/action?id=" + item.getId() + "&message=insert_success";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
