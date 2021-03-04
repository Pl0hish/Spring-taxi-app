package com.mnyshenko.taxiSpringApp.dto;

import com.mnyshenko.taxiSpringApp.model.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class OrderDTO {

    @Size(min = 5, max = 50, message = "{address.error}")
    @NotNull
    @NotBlank
    private String departureAddress;

    @Size(min = 5, max = 50, message = "{address.error}")
    @NotNull
    @NotBlank
    private String destinationAddress;

    @Min(value = 1, message = "{passengers.error}")
    @Max(value = 7)
    @NotNull
    private Integer passengers;

    private Car.Category category;
}
