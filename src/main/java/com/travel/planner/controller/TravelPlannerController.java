package com.travel.planner.controller;

import com.travel.planner.model.TravelPlan;
import com.travel.planner.model.TravelRequest;
import com.travel.planner.service.TravelPlannerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/travel")
public class TravelPlannerController {

    private static final Logger log = LoggerFactory.getLogger(TravelPlannerController.class);
    private final TravelPlannerService travelPlannerService;

    public TravelPlannerController(TravelPlannerService travelPlannerService) {
        this.travelPlannerService = travelPlannerService;
    }

    @PostMapping("/plan")
    public ResponseEntity<TravelPlan> generateTravelPlan(@Valid @RequestBody TravelRequest request) {
        log.info("Received travel plan request for destination: {}", request.getDestination());

        TravelPlan plan = travelPlannerService.generateTravelPlan(request);

        return ResponseEntity.ok(plan);
    }

    @GetMapping("/tips/{destination}")
    public ResponseEntity<Map<String, String>> getTravelTips(@PathVariable String destination) {
        log.info("Received request for travel tips for: {}", destination);

        String tips = travelPlannerService.generateTravelTips(destination);

        return ResponseEntity.ok(Map.of("destination", destination, "tips", tips));
    }

    @PostMapping("/suggest")
    public ResponseEntity<Map<String, String>> suggestDestinations(@RequestBody Map<String, String> request) {
        String preferences = request.getOrDefault("preferences", "");
        log.info("Received request to suggest destinations based on preferences: {}", preferences);

        String suggestions = travelPlannerService.suggestDestinations(preferences);

        return ResponseEntity.ok(Map.of("suggestions", suggestions));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Travel Planner AI"));
    }
}
