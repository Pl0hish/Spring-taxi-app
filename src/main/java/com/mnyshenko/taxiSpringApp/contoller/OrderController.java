package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import com.mnyshenko.taxiSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/make-order")
    public String showMakeOrder(@ModelAttribute("order") OrderDTO orderDTO, Model model) {

        model.addAttribute("categories", Car.Category.values());

        return "/make-order";
    }



    @PostMapping("/make-order")
    public String makeOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                            BindingResult bindingResult,
                            Model model) {

        model.addAttribute("categories", Car.Category.values());

        if (bindingResult.hasErrors()) {
            return "/make-order";
        }

        orderService.createOrder(orderDTO, userService.findUserById(1L));

        return "main";
    }
}
