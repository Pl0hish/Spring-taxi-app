package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ProfileController {

    private final OrderService orderService;

    @Autowired
    public ProfileController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        List<Order> ordersByUserId = orderService.getOrdersByUserId(currentUser.getId());

        model.addAttribute("user", currentUser);
        model.addAttribute("userOrders", ordersByUserId);

        return "user/profile-page";
    }
}
