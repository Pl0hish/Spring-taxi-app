package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mnyshenko.taxiSpringApp.constant.Constants.PAGE_SIZE;
import static com.mnyshenko.taxiSpringApp.model.Car.Category.BUSINESS;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CarService carService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CarService carService) {
        this.orderRepository = orderRepository;
        this.carService = carService;
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

    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id)
                .get();
    }

    public void createOrder(OrderDTO orderDTO, User user) {
        Car car = new Car(
                4,
                BUSINESS,
                "Driver");

        carService.save(car);

        orderRepository.save(
                new Order(
                        orderDTO.getDepartureAddress(),
                        orderDTO.getDestinationAddress(),
                        orderDTO.getPassengers(),
                        car,
                        user
                )
        );
    }


    public List<Order> getOrdersByUserId(Long id){
        return orderRepository.findAllByUserId(id).get();
    }

    public List<Order> getOrdersByCarId(Long id) {
        return orderRepository.findAllByCarId(id).get();
    }
}
