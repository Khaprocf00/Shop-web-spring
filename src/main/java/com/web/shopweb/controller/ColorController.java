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

import com.web.shopweb.dto.ColorDto;
import com.web.shopweb.service.ColorService;
import com.web.shopweb.util.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        ColorDto colorDto = new ColorDto();
        colorDto.setPage(page);
        colorDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(colorDto.getPage() - 1, colorDto.getMaxPageItem());
        colorDto.setTotalItem(colorService.getTotalItem());
        colorDto.setListResult(colorService.findAll(pageable));
        colorDto.setTotalPage((int) Math.ceil((double) colorDto.getTotalItem() / colorDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("model", colorDto);
        return "views/color/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, ColorDto colorDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            colorDto = colorService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", colorDto);
        return "views/color/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") ColorDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/color/action";
        }
        item = colorService.save(item);
        if (id != null) {
            return "redirect:/color/action?id=" + id + "&message=update_success";
        }
        return "redirect:/color/action?id=" + item.getId() + "&message=insert_success";
    }

}
