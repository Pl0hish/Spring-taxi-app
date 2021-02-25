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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mnyshenko.taxiSpringApp.constant.Role.ROLE_ADMIN;
import static com.mnyshenko.taxiSpringApp.constant.Role.ROLE_USER;

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
			User user = new User(
					"Igor",
					"Mnyshenko",
					"a",
					bCryptPasswordEncoder.encode("a"),
					ROLE_ADMIN
			);

			User user1 = new User(
					"Igor",
					"Mnyshenko",
					"u",
					bCryptPasswordEncoder.encode("u"),
					ROLE_USER
			);

			List<User> users = new ArrayList<>();

			for (int i = 0; i < 15; i++) {
				users.add(new User(
						"Firs name" + i,
						"Last name" + i,
						"email" + i + "@.com",
						bCryptPasswordEncoder.encode("password"),
						ROLE_USER
				));
			}

			List<Car> cars = new ArrayList<>();
			Car.Category[] categories = Car.Category.values();
			int[] seats = {3, 5, 7};

			for (int i = 0; i < 15; i++) {
				cars.add(new Car(
						seats[new Random().nextInt(3)],
						categories[new Random().nextInt(5)],
						"Driver name " + i
					)
				);
			}


			List<Order> orders = new ArrayList<>();
			for (int i = 0; i < 50; i++) {
				orders.add(new Order(
						"Departure address " + i,
						"Destination address " + i,
						new Random().nextInt(10 - 1 + 1) + 1,
						new Random().nextInt(30 - 2 + 1) + 2,
						(double)new Random().nextInt(200 - 50 + 1) + 50,
						(double)new Random().nextInt(10 - 1 + 1) + 1,
						cars.get(new Random().nextInt(cars.size())),
						users.get(new Random().nextInt(users.size()))
						)
				);
			}


			userRepository.saveAll(List.of(user, user1));
			userRepository.saveAll(users);

			carRepository.saveAll(cars);

			orderRepository.saveAll(orders);


		});
	}
}