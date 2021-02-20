package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer seats;

    @NotBlank
    @Size(min = 5, max = 50)
    private String driver;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ToString.Exclude
    @OneToMany(mappedBy = "car")
    private List<Order> order;

    public Car(Integer seats, Category category, String driver) {
        this.seats = seats;
        this.category = category;
        this.driver = driver;
    }

    public enum Category {
//        PREMIUM,
//        BUSINESS,
//        COMFORT,
//        STANDARD,
//        ECONOMY

        PREMIUM(20),
        BUSINESS(15),
        COMFORT(10),
        STANDARD(5),
        ECONOMY(1);

        private final int kmPrice;

        Category(int kmPrice) {
            this.kmPrice = kmPrice;
        }

        public int getKmPrice() {
            return kmPrice;
        }
    }

    public enum Status {
        AVAILABLE,
        ACTIVE,
        OFFLINE
    }
}