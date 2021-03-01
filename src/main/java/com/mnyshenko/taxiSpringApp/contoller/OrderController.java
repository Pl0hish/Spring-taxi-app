package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import com.mnyshenko.taxiSpringApp.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Random;


@Controller
@Log4j2
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/success")
    public String successPage(Model model) {
        model.addAttribute("timeToWait", new Random().nextInt(25 - 1 + 1) + 1);
        return "/order/order-success";
    }

    @PostMapping("/confirmationPage")
    public String orderSuccess(@ModelAttribute("confirmationOrder") Order order) {

        if (order == null) {
            return "redirect:/error";
        }

        orderService.saveOrder(order);
        return "redirect:/success";
    }

    @GetMapping("/confirmationPage")
    public String confirmationPage(@ModelAttribute("confirmationOrder") Order order) {
        return "/order/confirmation";
    }

    @GetMapping("/make-order")
    public String showMakeOrder(@ModelAttribute("order") OrderDTO orderDTO, Model model) {
        model.addAttribute("categories", Car.Category.values());
        orderDTO.setPassengers(1);
        return "order/make-order";
    }

    @PostMapping("/make-order")
    public String makeOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal) {


        redirectAttributes.addFlashAttribute("order", orderDTO);
        redirectAttributes.addFlashAttribute("categories", Car.Category.values());

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Car.Category.values());
            return "order/make-order";
        }

        try {
            Order order = orderService.createOrder(orderDTO, userService.findUserByEmail(principal.getName()));
            redirectAttributes.addFlashAttribute("confirmationOrder", order);
            return "redirect:/confirmationPage";
        } catch (CarException e) {
            return "redirect:/error";
        }
    }
}
