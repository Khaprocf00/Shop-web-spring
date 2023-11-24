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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.shopweb.dto.ProductDetailDto;
import com.web.shopweb.dto.ProductDto;
import com.web.shopweb.entity.ProductEntity;
import com.web.shopweb.repository.ProductRepository;
import com.web.shopweb.service.ColorService;
import com.web.shopweb.service.ProductDetailService;
import com.web.shopweb.service.ProductService;
import com.web.shopweb.service.SizeService;
import com.web.shopweb.util.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/productDetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("/{productId}")
    public String show(Model model, @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "1") int page, HttpServletRequest req,
            @PathVariable(name = "productId") Long productId) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        ProductEntity productEntity = new ProductEntity();
        productEntity = productRepository.findOneById(productId);
        productDetailDto.setPage(page);
        productDetailDto.setMaxPageItem(limit);
        Pageable pageable = PageRequest.of(productDetailDto.getPage() - 1, productDetailDto.getMaxPageItem());
        productDetailDto.setTotalItem(productDetailService.getTotalItem(productEntity));
        productDetailDto.setListResult(productDetailService.findAllByProduct(pageable, productEntity));
        productDetailDto.setTotalPage(
                (int) Math.ceil((double) productDetailDto.getTotalItem() / productDetailDto.getMaxPageItem()));
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }

        productDetailDto.setProductId(productId);
        model.addAttribute("model", productDetailDto);
        model.addAttribute("sumQty", productDetailService.sumQtyByProductId(productId));

        return "views/productDetail/list";
    }

    @GetMapping("/{productId}/action")
    public String action(@RequestParam(value = "id", required = false) Long id, ProductDetailDto productDetailDto,
            Model model, HttpServletRequest req, @PathVariable(name = "productId") Long productId) {
        if (id != null) {
            productDetailDto = productDetailService.findOne(id);
        }
        if (req.getParameter("message") != null) {
            Map<String, String> message = messageUtil.getMessage(req.getParameter("message"));
            model.addAttribute("message", message.get("message"));
            model.addAttribute("alert", message.get("alert"));
        }
        model.addAttribute("item", productDetailDto);
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());

        return "views/productDetail/action";
    }

    @PostMapping("/{productId}/action")
    public String saveOrUpdate(@Valid @ModelAttribute("item") ProductDetailDto item, BindingResult result,
            @RequestParam(value = "id", required = false) Long id, Model model,
            @PathVariable(name = "productId") Long productId)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "views/productDetail/action";
        }

        item = productDetailService.save(item);
        ProductDto productDto = productService.findOne(productId);
        productDto.setQty(productDetailService.sumQtyByProductId(productId));
        productDto = productService.save(productDto);
        if (id != null) {
            return "redirect:/productDetail/" + productId + "/action?id=" + id + "&message=update_success";
        }
        return "redirect:/productDetail/" + productId + "/action?id=" + item.getId() + "&message=insert_success";
    }

}
