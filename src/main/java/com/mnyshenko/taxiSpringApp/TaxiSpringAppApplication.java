package com.mnyshenko.taxiSpringApp;


import com.mnyshenko.taxiSpringApp.dao.CarRepository;
import com.mnyshenko.taxiSpringApp.dao.OrderRepository;
import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.model.Car;
import com.mnyshenko.taxiSpringApp.model.Order;
import com.mnyshenko.taxiSpringApp.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mnyshenko.taxiSpringApp.model.Role.ROLE_ADMIN;
import static com.mnyshenko.taxiSpringApp.model.Role.ROLE_USER;

@SpringBootApplication
public class TaxiSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiSpringAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner runApplication(UserRepository userRepository,
											OrderRepository orderRepository,
											CarRepository carRepository) {
		return (args -> {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Random random = new Random();

			List<User> users = new ArrayList<>();

			User user = User.builder()
					.firstName("Igor")
					.lastName("Mnyshenko")
					.email("a")
					.password(bCryptPasswordEncoder.encode("a"))
					.role(ROLE_ADMIN)
					.spentMoney(20.0)
					.build();

			User user1 = User.builder()
					.firstName("Igor")
					.lastName("Mnyshenko")
					.email("u")
					.password(bCryptPasswordEncoder.encode("u"))
					.role(ROLE_USER)
					.spentMoney(20.0)
					.build();

			for (int i = 0; i < 15; i++) {
				users.add(User.builder()
						.firstName("Firs name" + i)
						.lastName("Last name" + i)
						.email("email" + i + "@.com")
						.password(bCryptPasswordEncoder.encode("password"))
						.role(ROLE_USER)
						.spentMoney(i * 15.0)
						.build());
			}


			users.add(user);
			users.add(user1);
			userRepository.saveAll(users);

			List<Car> cars = new ArrayList<>();
			Car.Category[] categories = Car.Category.values();
			Car.Status[] statuses = Car.Status.values();
			int[] seats = {3, 5, 7};

//			for (int i = 0; i < 15; i++) {
//				cars.add(Car.builder()
//						.seats(3)
//						.category(Car.Category.BUSINESS)
//						.driver("Driver name " + i)
//						.status(Car.Status.ACTIVE)
//						.build());
//			}
//
//			for (int i = 0; i < 15; i++) {
//				cars.add(Car.builder()
//						.seats(7)
//						.category(Car.Category.COMFORT)
//						.driver("Driver name " + i)
//						.status(Car.Status.ACTIVE)
//						.build());
//			}
//			carRepository.saveAll(cars);


			for (int i = 0; i < 50; i++) {
				cars.add(Car.builder()
						.seats(seats[random.nextInt(3)])
						.category(categories[random.nextInt(5)])
						.driver("Driver name " + i)
						.status(statuses[random.nextInt(3)])
						.build());
			}

			carRepository.saveAll(cars);
			LocalDateTime localDateTime = LocalDateTime.now();

			List<Order> orders = new ArrayList<>();
			for (int i = 0; i < 50; i++) {
				orders.add(Order.builder()
						.departureAddress("Departure address " + i)
						.destinationAddress("Destination address " + i)
						.passengers(random.nextInt(7 - 1 + 1) + 1)
						.distance(random.nextInt(30 - 2 + 1) + 2)
						.price((double)random.nextInt(200 - 50 + 1) + 50)
						.car(cars.get(random.nextInt(cars.size())))
						.user(users.get(random.nextInt(users.size())))
						.date(localDateTime)
						.build());
			}

			orderRepository.saveAll(orders);
		}
		);
	}
}