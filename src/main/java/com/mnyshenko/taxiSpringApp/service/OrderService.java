package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.mnyshenko.taxiSpringApp.constant.Constants.PAGE_SIZE;

@Service
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final CarService carService;
    private final UserService userService;

    public void saveOrder(Order order) {
        order.setDate(LocalDateTime.now());
        userService.updateSpentMoney(order.getUser(), order.getPrice());
        carService.updateStatus(order.getCar());
        orderRepository.save(order);
    }

    @Autowired
    public OrderService(OrderRepository orderRepository, CarService carService, UserService userService) {
        this.orderRepository = orderRepository;
        this.carService = carService;
        this.userService = userService;
    }



    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Page<Order> getPaginated(int pageNo, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return orderRepository.findAll(pageable);
    }

    public Order createOrder(OrderDTO orderDTO, User user) throws CarException {
        Car car = carService.findFirstByCategory(orderDTO.getCategory(), orderDTO.getPassengers());
        double spentMoney = user.getSpentMoney();

        orderDTO.setDistance();

        return new Order(
                        orderDTO.getDepartureAddress(),
                        orderDTO.getDestinationAddress(),
                        orderDTO.getPassengers(),
                        orderDTO.getDistance(),
                        orderDTO.getPrice(),
                        spentMoney,
                        car,
                        user);
    }


    public List<Order> getOrdersByUserId(Long id){
        return orderRepository.findTop5ByUserIdOrderByDateDesc(id).get();
    }

    public List<Order> getAllOrdersByUserId(Long id){
        return orderRepository.findAllByUserId(id).get();
    }

    public List<Order> getAllOrdersByCarId(Long id) {
        return orderRepository.findAllByCarId(id).get();
    }
}
