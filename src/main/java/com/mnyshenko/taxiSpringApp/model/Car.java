package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seats;
    private String driver;
    private int kmPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "car")
    private List<Order> orders;

    @Builder
    public Car(int seats, String driver, Category category, Status status) {
        this.seats = seats;
        this.driver = driver;
        this.category = category;
        this.kmPrice = category.getKmPrice();
        this.status = status;
    }

    public enum Category {
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