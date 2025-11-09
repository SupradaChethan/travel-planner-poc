package com.travel.planner.model;

import java.util.List;

public class DayItinerary {

    private Integer day;
    private String theme;
    private List<Activity> activities;

    public DayItinerary() {}

    public DayItinerary(Integer day, String theme, List<Activity> activities) {
        this.day = day;
        this.theme = theme;
        this.activities = activities;
    }

    public Integer getDay() { return day; }
    public void setDay(Integer day) { this.day = day; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> activities) { this.activities = activities; }
}
