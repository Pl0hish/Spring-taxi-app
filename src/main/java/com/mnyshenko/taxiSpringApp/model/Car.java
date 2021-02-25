package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seats;

    private String driver;

    private Integer kmPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToMany(mappedBy = "car")
    private List<Order> order;

    public Car(Integer seats,
               Category category,
               String driver) {
        this.seats = seats;
        this.category = category;
        this.driver = driver;
        this.status = Status.AVAILABLE;
        this.kmPrice = category.getKmPrice();
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