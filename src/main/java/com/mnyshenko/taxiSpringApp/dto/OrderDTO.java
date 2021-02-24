package com.mnyshenko.taxiSpringApp.dto;

import com.mnyshenko.taxiSpringApp.model.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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

    @Setter(AccessLevel.NONE)
    private String category;

    public OrderDTO() {
        passengers = 1;
    }

    public void setCategory(String category) {
        boolean isValid = false;

        for (Car.Category carCategory : Car.Category.values()) {
            if (carCategory.name()
                    .equals(category)) {
                isValid = true;
                break;
            }
        }

        this.category = isValid ? category : Car.Category.PREMIUM.name();
    }
}
