package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;

    @Autowired
    public AdminController(OrderRepository orderRepository, UserRepository userRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/all-orders")
    public String getAllOrders(Model model) {

        carRepository.save(new Car(4, Car.Category.BUSINESS));
        carRepository.findById(1L);

        User name = new User("name");
        name.setSpentMoney(0);
        userRepository.save(name);

        orderRepository.save(new Order("1",
                "2",
                150.0,
                LocalDateTime.now(),
                carRepository.findById(1L).get(),
                userRepository.findById(1L).get()));

        orderRepository.save(new Order("1",
                "2",
                150.0,
                LocalDateTime.now(),
                carRepository.findById(1L).get(),
                userRepository.findById(1L).get()));

        orderRepository.save(new Order("1",
                "2",
                150.0,
                LocalDateTime.now(),
                carRepository.findById(1L).get(),
                userRepository.findById(1L).get()));

        orderRepository.save(new Order("1",
                "2",
                150.0,
                LocalDateTime.now(),
                carRepository.findById(1L).get(),
                userRepository.findById(1L).get()));

        orderRepository.save(new Order("1",
                "2",
                150.0,
                LocalDateTime.now(),
                carRepository.findById(1L).get(),
                userRepository.findById(1L).get()));



        model.addAttribute("orders", orderRepository.findAll());

        return "orders";
    }
}
