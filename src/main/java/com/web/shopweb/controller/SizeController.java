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

import com.web.shopweb.dto.SizeDto;
import com.web.shopweb.service.impl.SizeServiceImpl;
import com.web.shopweb.util.MessageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/size")
public class SizeController {
    @Autowired
    private SizeServiceImpl sizeService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req) {
        SizeDto sizeDto = new SizeDto();
        sizeDto.setPage(page);
        sizeDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(sizeDto.getPage() - 1, sizeDto.getMaxPageItem());
        sizeDto.setTotalItem(sizeService.getTotalItem());
        sizeDto.setListResult(sizeService.findAll(pageable));
        sizeDto.setTotalPage((int) Math.ceil((double) sizeDto.getTotalItem() / sizeDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("model", sizeDto);
        return "views/size/list";
    }

    @GetMapping("/action")
    public String action(@RequestParam(value = "id", required = false) Long id, SizeDto sizeDto,
            Model model,
            HttpServletRequest req) {
        if (id != null) {
            sizeDto = sizeService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", sizeDto);
        return "views/size/action";
    }

    @PostMapping("/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") SizeDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/size/action";
        }
        item = sizeService.save(item);
        if (id != null) {
            return "redirect:/size/action?id=" + id + "&message=update_success";
        }
        return "redirect:/size/action?id=" + item.getId() + "&message=insert_success";
    }

}
