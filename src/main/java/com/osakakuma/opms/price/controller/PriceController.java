package com.osakakuma.opms.price.controller;

import com.osakakuma.opms.price.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpi/admin/price")
public class PriceController {
    private final PriceService priceService;


}
