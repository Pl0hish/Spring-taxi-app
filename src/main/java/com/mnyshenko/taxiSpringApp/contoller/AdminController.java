package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.UserService;
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

    @Autowired
    private UserService userService;

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

        carRepository.save(new Car(4, Car.Category.BUSINESS, "DRIVER"));
        carRepository.findById(1L);

        User name = new User("name", "last name", "email");
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

        return "admin/orders";
    }


    @GetMapping("/all-users")
    public String getUsers(Model model) {

        userService.save(new User("name1", "lastname1", "gmail"));
        userService.save(new User("name2", "lastname1", "gmail"));
        userService.save(new User("name3", "lastname1", "gmail"));
        userService.save(new User("name4", "lastname1", "gmail"));
        userService.save(new User("name5", "lastname1", "gmail"));
        userService.save(new User("name6", "lastname1", "gmail"));

        model.addAttribute("users", userService.getUsers());

        return "admin/users";
    }


    @GetMapping("/all-cars")
    public String getCars(Model model) {

        carRepository.save(new Car(4, Car.Category.PREMIUM, "driver"));
        carRepository.save(new Car(4, Car.Category.STANDARD, "driver"));
        carRepository.save(new Car(4, Car.Category.COMFORT, "driver"));
        carRepository.save(new Car(4, Car.Category.PREMIUM, "driver"));
        carRepository.save(new Car(4, Car.Category.BUSINESS, "driver"));
        carRepository.save(new Car(4, Car.Category.ECONOMY, "driver"));

        model.addAttribute("cars", carRepository.findAll());
        return "admin/cars";
    }

}
