package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/booking")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/new-booking")
    public String makeBooking() {
        return "new-booking";
    }

    @GetMapping("/all-bookings")
    public String getBookings(Model model) {
        Car car = new Car();
        User user = new User();

        orderService.save(new Order(
                "Address",
                "destination Address",
                10.0,
                LocalDateTime.now(),
                car,
                user
        ));

        orderService.save(new Order(
                "Address",
                "destination Address",
                15.0,
                LocalDateTime.now(),
                car,
                user
        ));

        orderService.save(new Order(
                "Address",
                "destination Address",
                17.0,
                LocalDateTime.now(),
                car,
                user
        ));

        orderService.save(new Order(
                "Address",
                "destination Address",
                24.3,
                LocalDateTime.now(),
                car,
                user
        ));

        orderService.save(new Order(
                "Address",
                "destination Address",
                10.0,
                LocalDateTime.now(),
                car,
                user
        ));

        model.addAttribute("bookings", orderService.findAll());

        return "bookings";
    }
}
