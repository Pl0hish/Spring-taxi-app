package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class CarController {

    private CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

//    @GetMapping("/all-cars")
//    public String getCars(Model model) {
//
//        carRepository.save(new Car(4, Car.Category.PREMIUM, "driver"));
//        carRepository.save(new Car(4, Car.Category.STANDARD, "driver"));
//        carRepository.save(new Car(4, Car.Category.COMFORT, "driver"));
//        carRepository.save(new Car(4, Car.Category.PREMIUM, "driver"));
//        carRepository.save(new Car(4, Car.Category.BUSINESS, "driver"));
//        carRepository.save(new Car(4, Car.Category.ECONOMY, "driver"));
//
//        model.addAttribute("cars", carRepository.findAll());
//        return "admin/cars";
//    }
}
