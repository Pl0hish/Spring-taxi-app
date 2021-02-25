package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
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
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Random;


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
        model.addAttribute("confirmation", false);
        model.addAttribute("categories", Car.Category.values());
        return "order/make-order";
    }

    @PostMapping("/order-success")
    public String orderSuccess(@ModelAttribute("confirmationOrder") Order order) {
        orderService.saveOrder(order);;
        return "redirect:/profile";
    }


    @PostMapping("/make-order")
    public String makeOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  Principal principal) {

        model.addAttribute("categories", Car.Category.values());
        model.addAttribute("confirmation", false);
        orderDTO.setDistance();

        if (bindingResult.hasErrors()) {
            return "order/make-order";
        }


        try {
            Order order = orderService.createOrder(orderDTO, userService.findUserByEmail(principal.getName()));
            model.addAttribute("confirmationOrder", order);
            model.addAttribute("timeToWait", new Random().nextInt(20 - 1 + 1) + 1);
            model.addAttribute("confirmation", true);
            return "order/make-order";
        } catch (CarException e) {
            return "/error";
        }
    }
}
