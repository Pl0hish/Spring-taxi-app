package com.mnyshenko.taxiSpringApp.dao;

import com.mnyshenko.taxiSpringApp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

//    public Optional<Car> findCarById(int id);
}
