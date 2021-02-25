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
                 Integer distance,
                 Integer price,
                 Integer spentMoney,
                 Car car,
                 User user) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.passengers = passengers;
        this.car = car;
        this.user = user;
        this.date = LocalDateTime.now();
        this.distance = distance;
        this.price = (spentMoney > 1000 ? 0.20 : 1)  * price;
    }


    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }
}
