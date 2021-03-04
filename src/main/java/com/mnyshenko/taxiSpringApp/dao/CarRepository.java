package com.mnyshenko.taxiSpringApp.dao;

import com.mnyshenko.taxiSpringApp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findFirstByStatusAndSeatsGreaterThanEqual(Car.Status status, int seats);

    List<Car> findAllByCategoryAndStatus(Car.Category category, Car.Status status);

    Optional<Car> getFirstCarByCategoryAndSeatsGreaterThanEqualAndStatus(Car.Category category, int seats, Car.Status status);
}
