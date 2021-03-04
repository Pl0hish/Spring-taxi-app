package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    private double price;

    private int passengers;

    private int distance;

    @Getter(AccessLevel.NONE)
    private LocalDateTime date;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User user;

    @Builder
    public Order(String departureAddress, String destinationAddress, double price, int passengers, int distance, LocalDateTime date, Car car, User user) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.passengers = passengers;
        this.distance = distance;
        this.date = date;
        this.car = car;
        this.user = user;
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }
}
