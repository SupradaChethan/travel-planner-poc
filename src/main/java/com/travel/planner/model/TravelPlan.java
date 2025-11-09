package com.travel.planner.model;

import java.util.List;

public class TravelPlan {

    private String destination;
    private Integer numberOfDays;
    private String overview;
    private List<DayItinerary> itinerary;
    private List<String> recommendations;
    private String budgetEstimate;

    public TravelPlan() {}

    public TravelPlan(String destination, Integer numberOfDays, String overview,
                      List<DayItinerary> itinerary, List<String> recommendations, String budgetEstimate) {
        this.destination = destination;
        this.numberOfDays = numberOfDays;
        this.overview = overview;
        this.itinerary = itinerary;
        this.recommendations = recommendations;
        this.budgetEstimate = budgetEstimate;
    }

    public static Builder builder() { return new Builder(); }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Integer getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(Integer numberOfDays) { this.numberOfDays = numberOfDays; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public List<DayItinerary> getItinerary() { return itinerary; }
    public void setItinerary(List<DayItinerary> itinerary) { this.itinerary = itinerary; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    public String getBudgetEstimate() { return budgetEstimate; }
    public void setBudgetEstimate(String budgetEstimate) { this.budgetEstimate = budgetEstimate; }

    public static class Builder {
        private String destination;
        private Integer numberOfDays;
        private String overview;
        private List<DayItinerary> itinerary;
        private List<String> recommendations;
        private String budgetEstimate;

        public Builder destination(String destination) { this.destination = destination; return this; }
        public Builder numberOfDays(Integer numberOfDays) { this.numberOfDays = numberOfDays; return this; }
        public Builder overview(String overview) { this.overview = overview; return this; }
        public Builder itinerary(List<DayItinerary> itinerary) { this.itinerary = itinerary; return this; }
        public Builder recommendations(List<String> recommendations) { this.recommendations = recommendations; return this; }
        public Builder budgetEstimate(String budgetEstimate) { this.budgetEstimate = budgetEstimate; return this; }

        public TravelPlan build() {
            return new TravelPlan(destination, numberOfDays, overview, itinerary, recommendations, budgetEstimate);
        }
    }
}
