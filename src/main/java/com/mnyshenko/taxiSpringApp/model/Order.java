package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 50)
    private String departureAddress;

    @NotBlank
    @Size(min = 5, max = 50)
    private String destinationAddress;

    @NotNull
    private Double price;

    @NotNull
    @Getter(AccessLevel.NONE)
    private LocalDateTime date;

    @NotNull
    @ManyToOne
    private Car car;

    @NotNull
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

    public Timestamp getDate() {
        return Timestamp.valueOf(date.withNano(0));
    }
}
