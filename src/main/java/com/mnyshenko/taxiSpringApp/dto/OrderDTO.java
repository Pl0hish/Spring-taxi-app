package com.mnyshenko.taxiSpringApp.dto;

import com.mnyshenko.taxiSpringApp.model.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class OrderDTO {

    @Size(min = 5, max = 50, message = "{address.error}")
    private String departureAddress;

    @Size(min = 5, max = 50, message = "{address.error}")
    private String destinationAddress;

    @Min(value = 1, message = "{passengers.error}")
    @Max(value = 30)
    private int passengers;

    private Car.Category category;

    @Setter(AccessLevel.NONE)
    private Integer distance;

    public double setDistance() {
        return this.distance = new Random().nextInt(30 - 2 + 1) + 2;
    }

    public int getPrice() {
        return distance * category.getKmPrice();
    }
}
