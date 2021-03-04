package com.mnyshenko.taxiSpringApp.messages;

public class Messages {

    public final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    public final static String USER_ID_NOT_FOUND_MSG = "User with id %d not found";
    public final static String USER_ALREADY_EXISTS_MSG = "User with email %s already exists";

    public final static String NO_CAR_AVAILABLE = "Car with category %s, seats %d, status %s not found";

    public final static String NO_ALTERNATIVES_FOUND = "No alternatives found for given order";

    public final static String ADMIN_ALL_ORDERS = "All orders requested by admin";
    public final static String PREPARING_ORDER = "Preparing an order";
    public final static String PREPARING_ALTERNATIVES = "Preparing alternative orders";
    public final static String CREATING_ORDER = "Creating an order";
    public final static String CREATING_MULTIPLE_ORDERS = "Creating multiple orders";
}
