package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.CarService;
import com.mnyshenko.taxiSpringApp.service.OrderService;
import com.mnyshenko.taxiSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final CarService carService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService, CarService carService) {
        this.userService = userService;
        this.orderService = orderService;
        this.carService = carService;
    }

    @GetMapping()
    public String getAdminPanel() {
        return "admin/admin-panel";
    }

    @GetMapping("/all-orders")
    public String getOrders(@RequestParam("page") Optional<Integer> pageNo,
                            @RequestParam("sortBy") Optional<String> sortBy,
                            @RequestParam("direction") Optional<String> direction,
                            Model model) {
        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        //TODO: REVERSE ORDERING ON PAGE
        String dir = direction.isEmpty() || direction.get().equalsIgnoreCase("acs") ?
                "asc" : "desc";

        Page<Order> page = orderService.getPaginated(currentPage, sort, dir);
        List<Order> orders = page.getContent();

        model.addAttribute("orders", orders);
        paginationHelper(model, currentPage, sort, dir, page.getTotalPages(), page.getTotalElements(), page);

        return "admin/order/orders";
    }


    @GetMapping("/all-users")
    public String getUsers(@RequestParam("page") Optional<Integer> pageNo,
                           @RequestParam("sortBy") Optional<String> sortBy,
                           @RequestParam("direction") Optional<String> direction,
                           Model model) {

        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        //TODO: REVERSE ORDERING ON PAGE
        String dir = direction.isEmpty() || direction.get().equalsIgnoreCase("acs") ?
                "asc" : "desc";

        Page<User> page = userService.getPaginated(currentPage, sort, dir);
        List<User> users = page.getContent();

        model.addAttribute("users", users);
        paginationHelper(model, currentPage, sort, dir, page.getTotalPages(), page.getTotalElements(), page);

        return "admin/user/users";
    }

    private void paginationHelper(Model model, int currentPage, String sort, String dir, int totalPages, long totalElements, Page<?> page) {
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalElements);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sort);
        model.addAttribute("direction", dir);
        model.addAttribute("reverseDirection", dir.equals("asc") ? "desc" : "asc");
    }


    @GetMapping("/all-cars")
    public String getCars(@RequestParam("page") Optional<Integer> pageNo,
                          @RequestParam("sortBy") Optional<String> sortBy,
                          @RequestParam("direction") Optional<String> direction,
                          Model model) {
        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        //TODO: REVERSE ORDERING ON PAGE
        String dir = direction.isEmpty() || direction.get().equalsIgnoreCase("acs") ?
                "asc" : "desc";

        Page<Car> page = carService.getPaginated(currentPage, sort, dir);
        List<Car> cars = page.getContent();

        model.addAttribute("cars", cars);
        paginationHelper(model, currentPage, sort, dir, page.getTotalPages(), page.getTotalElements(), page);

        return "admin/car/cars";
    }

    @GetMapping("/car/{id}")
    public String getCar(@PathVariable Long id, Model model) {

        model.addAttribute("car" , carService.findCarById(id));
        model.addAttribute("carsOrders" , orderService.getOrdersByCarId(id));

        return "admin/car/car-info";
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable Long id, Model model) {

        model.addAttribute("order" , orderService.findOrderById(id));

        return "admin/order/order-info";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model) {

        model.addAttribute("user" ,userService.findUserById(id));
        model.addAttribute("userOrders", orderService.getOrdersByUserId(id));

        return "admin/user/user-info";
    }
}
