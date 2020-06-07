package net.jaumebalmes.grincon17.wannago.models;

import java.io.Serializable;

public class Event implements Serializable {

    private long id;

    private String img;

    private String name;

    private String date;

    private String time;

    private String location;

    private String description;

    public Event(String img, String name, String date, String time, String location, String description) {
        this.img = img;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}