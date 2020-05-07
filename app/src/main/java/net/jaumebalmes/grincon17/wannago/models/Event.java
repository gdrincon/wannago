package net.jaumebalmes.grincon17.wannago.models;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("img")
    private String img;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    public Event(String img, String name, String date, String time, String location, String description) {
        this.img = img;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
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