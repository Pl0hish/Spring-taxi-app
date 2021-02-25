package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import com.mnyshenko.taxiSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class ProfileController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/profile")
    public String getUser(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());

        model.addAttribute("user" ,user);
        model.addAttribute("userOrders", orderService.getOrdersByUserId(user.getId()));

        return "user/profile-page";
    }
}
