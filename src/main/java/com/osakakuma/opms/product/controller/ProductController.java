package com.osakakuma.opms.product.controller;

import com.osakakuma.opms.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpi/admin/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


}
