package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.dto.OrderDTO;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.messages.Messages;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.mnyshenko.taxiSpringApp.messages.Messages.*;

@Service
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public final static int PAGE_SIZE = 5;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void saveOrders(List<Order> orders) {
        User user = orders.get(0).getUser();
        List<Car> cars = new ArrayList<>();

        double changeBalance = user.getSpentMoney();
        for (Order order : orders) {
            changeBalance += order.getPrice();
            order.setDate(LocalDateTime.now());

            Car orderCar = order.getCar();
            orderCar.setStatus(Car.Status.ACTIVE);
            cars.add(orderCar);
        }

        user.setSpentMoney(changeBalance);

        userRepository.save(user);
        carRepository.saveAll(cars);
        orderRepository.saveAll(orders);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Map<String, List<Order>> findAlternatives(Order order) throws CarException {
        log.info(PREPARING_ALTERNATIVES);
        int passengers = order.getPassengers();
        Car.Category desiredCategory = order.getCar()
                .getCategory();

        Map<String, List<Order>> alternatives = new HashMap<>();
        alternatives.put("otherCarOrder", Collections.emptyList());
        alternatives.put("desiredCarsOrder", Collections.emptyList());

        List<Car> otherCategoryCar = getOtherCategoryCar(passengers);
        if (otherCategoryCar.size() == 1) {
            List<Order> otherCarOrder = makeOrders(order, otherCategoryCar);
            alternatives.put("otherCarOrder", otherCarOrder);
        }

        List<Car> desiredCategoryCars = getDesiredCategoryCars(passengers, desiredCategory);
        if (desiredCategoryCars.size() > 1) {
            List<Order> desiredCarsOrder = makeOrders(order, desiredCategoryCars);
            alternatives.put("desiredCarsOrder", desiredCarsOrder);
        }

        if (alternatives.size() == 0) {
            throw new CarException((NO_ALTERNATIVES_FOUND));
        }

        return alternatives;
    }


    private List<Order> makeOrders(Order order, List<Car> cars) {
        log.info(CREATING_MULTIPLE_ORDERS);
        List<Order> orders = new ArrayList<>();
        int leftPassengersToSeat = order.getPassengers();
        int passengersForCar;
        double price;

        for (Car car : cars) {
            price = car.getKmPrice() * order.getDistance();
            passengersForCar = Math.min(car.getSeats(), leftPassengersToSeat);
            leftPassengersToSeat = leftPassengersToSeat - passengersForCar;

            orders.add(Order.builder()
                    .departureAddress(order.getDepartureAddress())
                    .destinationAddress(order.getDestinationAddress())
                    .car(car)
                    .user(order.getUser())
                    .distance(order.getDistance())
                    .price(price)
                    .passengers(passengersForCar)
                    .build());
        }

        return orders;
    }

    private List<Car> getDesiredCategoryCars(int passengers, Car.Category desiredCategory) {
        List<Car> allGivenCategoryCars = carRepository.findAllByCategoryAndStatus(desiredCategory, Car.Status.AVAILABLE);
        List<Car> desiredCars = new ArrayList<>();

        int counter = 0;
        for (Car car : allGivenCategoryCars) {
            counter += car.getSeats();
            desiredCars.add(car);

            if (counter >= passengers) {
                return desiredCars;
            }
        }

        return new ArrayList<>();
    }

    private List<Car> getOtherCategoryCar(int passengers) {
        Optional<Car> car = carRepository.findFirstByStatusAndSeatsGreaterThanEqual(Car.Status.AVAILABLE, passengers);

        return car.map(List::of)
                .orElse(Collections.emptyList());
    }


    @Transactional(rollbackFor = {Exception.class})
    public void createOrder(Order order) throws CarException {
        log.info(CREATING_ORDER);

        Car.Category category = order.getCar().getCategory();
        int passengers = order.getPassengers();
        Car.Status status = Car.Status.AVAILABLE;

        Car car = carRepository.getFirstCarByCategoryAndSeatsGreaterThanEqualAndStatus(category, passengers, status)
                .orElseThrow(() -> new CarException(String.format(NO_CAR_AVAILABLE, category, passengers, status)));

        car.setStatus(status);
        carRepository.save(car);

        User currentUser = order.getUser();
        currentUser.setSpentMoney(currentUser.getSpentMoney() + order.getPrice());
        userRepository.save(currentUser);

        order.setCar(car);
        order.setDate(LocalDateTime.now());

        orderRepository.save(order);
    }

    public Page<Order> getPaginated(int pageNo, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return orderRepository.findAll(pageable);
    }

    public Order prepareOrder(OrderDTO orderDTO, User user) {
        log.info(PREPARING_ORDER);

        Car car = Car.builder()
                .category(orderDTO.getCategory())
                .build();

        int distance = new Random().nextInt(30 - 2 + 1) + 1;
        double discount = user.getSpentMoney() > 1000 ? 0.20 : 1;
        int kmPrice = orderDTO.getCategory()
                .getKmPrice();

        double price = distance * kmPrice * discount;

        return Order.builder()
                .user(user)
                .departureAddress(orderDTO.getDepartureAddress())
                .destinationAddress(orderDTO.getDestinationAddress())
                .price(price)
                .passengers(orderDTO.getPassengers())
                .distance(distance)
                .car(car)
                .build();
    }


    public List<Order> getOrdersByUserId(Long id) {
        return orderRepository.findTop5ByUserIdOrderByDateDesc(id);
    }
}
