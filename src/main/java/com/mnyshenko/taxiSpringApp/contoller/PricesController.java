package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PricesController {

    @GetMapping("/prices")
    public String getPrices(Model model) {
        model.addAttribute("categories", Car.Category.values());
        return "/prices";
    }
}
