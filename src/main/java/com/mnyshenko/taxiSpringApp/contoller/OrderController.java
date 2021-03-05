package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Controller
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/alternative-success")
    public String alternativeSuccess(@RequestParam("alternative") String alternative, HttpSession session) {
        Map<String, List<Order>> alternatives = (Map<String, List<Order>>) session.getAttribute("alternatives");
        orderService.saveOrders(alternatives.get(alternative));
        session.removeAttribute("alternatives");

        return "redirect:/final-success-page";
    }

    @GetMapping("/alternatives")
    public String findAlternatives(HttpSession session, Model model) {
        Order order = (Order) session.getAttribute("confirmationOrder");
        session.removeAttribute("confirmationOrder");
        Map<String, List<Order>> alternatives;

        try {
            alternatives = orderService.findAlternatives(order);

            List<Order> otherCarOrder = alternatives.get("otherCarOrder");
            List<Order> desiredCarsOrder = alternatives.get("desiredCarsOrder");

            session.setAttribute("alternatives", alternatives);

            model.addAttribute("otherCarOrder", otherCarOrder);
            model.addAttribute("desiredCarsOrder", desiredCarsOrder);

            return "/order/alternatives";
        } catch (CarException e) {
            System.out.println(e.getMessage());
            return "/order/no-alternatives";
        }
    }

    @GetMapping("/order-failure")
    public String orderFailurePage() {
        return "/order/order-failure";
    }

    @GetMapping("/final-success-page")
    public String showSuccessPage(Model model) {
        model.addAttribute("timeToWait", new Random().nextInt(25 - 1 + 1) + 1);
        return "/order/order-success";
    }

    @PostMapping("/success")
    public String createOrder(HttpSession session) {
        Order order = (Order) session.getAttribute("confirmationOrder");

        if (order == null) {
            return "redirect:/error";
        }

        try {
            orderService.createOrder(order);
        } catch (CarException e) {
            return "redirect:/order-failure";
        }

        session.removeAttribute("confirmationOrder");

        return "redirect:/final-success-page";
    }

    @GetMapping("/confirmationPage")
    public String confirmationPage(@ModelAttribute("confirmationOrder") Order order) {
        return "/order/confirmation";
    }

    @GetMapping("/make-order")
    public String showMakeOrder(@ModelAttribute("order") OrderDTO orderDTO, Model model) {
        model.addAttribute("categories", Car.Category.values());
        log.info("show make order method");
        return "order/make-order";
    }

    @PostMapping("/make-order")
    public String makeOrder(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            Authentication authentication,
                            HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Car.Category.values());
            return "order/make-order";
        }

        Order order = orderService.prepareOrder(orderDTO, (User) authentication.getPrincipal());

        httpSession.setAttribute("confirmationOrder", order);
        redirectAttributes.addFlashAttribute("confirmationOrder", order);

        return "redirect:/confirmationPage";
    }
}
