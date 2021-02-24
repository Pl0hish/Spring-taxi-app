package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departureAddress;

    private String destinationAddress;

    private Double price;

    private Integer passengers;

    private Integer distance;

    @Getter(AccessLevel.NONE)
    private LocalDateTime date;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User user;

    public Order(String departureAddress,
                 String destinationAddress,
                 Integer passengers,
                 Car car,
                 User user) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.passengers = passengers;
        this.car = car;
        this.user = user;
        this.date = LocalDateTime.now();
        this.distance = generateDistance(2, 30);
        this.price = countPrice();
    }

    public int generateDistance(int from, int to) {
        return new Random().nextInt(to - from + 1) + from;
    }

    public double countDiscount() {
        return user.getSpentMoney() > 1000 ?
                0.15 : 0.5;
    }

    public double countPrice() {
        return countDiscount() *
                (distance * car.getCategory().getKmPrice());
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }
}
