package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.exception.CarException;
import com.mnyshenko.taxiSpringApp.model.Car;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mnyshenko.taxiSpringApp.constant.Constants.PAGE_SIZE;

@Service
@Log4j2
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Page<Car> getPaginated(int pageNo, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return carRepository.findAll(pageable);
    }

    //TODO to do something
    public Car findFirstByCategory(Car.Category category, Integer seats) throws CarException {
        return carRepository.findFirstByCategoryAndStatusAndSeatsGreaterThanEqual(category, Car.Status.AVAILABLE, seats)
                .orElseThrow(() -> new CarException("No car available with given category"));
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public Car findCarById(Long id) {
        return carRepository.findCarById(id).get();
    }


    public void updateStatus(Car car) {
        car.setStatus(Car.Status.ACTIVE);
        carRepository.save(car);
    }
}
