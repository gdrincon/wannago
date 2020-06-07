package net.jaumebalmes.grincon17.wannago.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Car implements Serializable {

    private long id;
    private User driver;
    private ArrayList<User> passengers;
    private ArrayList<Event> events;
    private int seats;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String departurePlace;


}
