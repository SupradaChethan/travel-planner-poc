package com.travel.planner.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TravelRequest {

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Number of days is required")
    private Integer numberOfDays;

    private String interests; // e.g., "culture, food, adventure"
    private String budget; // e.g., "budget", "moderate", "luxury"
    private String travelStyle; // e.g., "relaxed", "packed", "balanced"

    public TravelRequest() {}

    public TravelRequest(String destination, Integer numberOfDays, String interests, String budget, String travelStyle) {
        this.destination = destination;
        this.numberOfDays = numberOfDays;
        this.interests = interests;
        this.budget = budget;
        this.travelStyle = travelStyle;
    }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Integer getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(Integer numberOfDays) { this.numberOfDays = numberOfDays; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public String getTravelStyle() { return travelStyle; }
    public void setTravelStyle(String travelStyle) { this.travelStyle = travelStyle; }
}
