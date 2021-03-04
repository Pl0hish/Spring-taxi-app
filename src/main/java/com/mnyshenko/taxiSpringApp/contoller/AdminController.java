package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.messages.Messages;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static com.mnyshenko.taxiSpringApp.messages.Messages.ADMIN_ALL_ORDERS;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    private final OrderService orderService;

    @Autowired
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all-orders")
    public String getAllOrders(@RequestParam("page") Optional<Integer> pageNo,
                               @RequestParam("sortBy") Optional<String> sortBy,
                               @RequestParam("direction") Optional<String> direction,
                               Model model) {

        log.info(ADMIN_ALL_ORDERS);

        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        String dir = "asc".equalsIgnoreCase(direction.orElse("asc")) ? "asc" : "desc";

        Page<Order> page = orderService.getPaginated(currentPage, sort, dir);
        List<Order> orders = page.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sort);
        model.addAttribute("direction", dir);
        model.addAttribute("reverseDirection", dir.equals("asc") ? "desc" : "asc");

        return "admin/order/orders";
    }

}
