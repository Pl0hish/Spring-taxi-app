package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "date", columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User user;

    public Order(String departureAddress,
                 String destinationAddress,
                 Double price,
                 LocalDateTime date,
                 Car car,
                 User user) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.date = date;
        this.car = car;
        this.user = user;
    }

    public double getDiscount() {
        if (user.getSpentMoney() > 1000) {
            return 0.15;
        }

        return 0.5;
    }

    public double getPrice() {
        double kilometers = new Random().nextInt(30 - 2 + 1) + 2;
        return getDiscount() *
                (kilometers * car.getCategory().getKmPrice());
    }
}
