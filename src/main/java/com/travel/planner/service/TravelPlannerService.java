package com.travel.planner.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.travel.planner.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TravelPlannerService {

    private static final Logger log = LoggerFactory.getLogger(TravelPlannerService.class);
    private final Kernel kernel;

    public TravelPlannerService(Kernel kernel) {
        this.kernel = kernel;
    }

    public TravelPlan generateTravelPlan(TravelRequest request) {
        try {
            String prompt = buildPrompt(request);
            String response = getChatCompletion(prompt);

            log.info("AI Response: {}", response);

            // Parse the response and create a TravelPlan
            return parseTravelPlan(response, request);
        } catch (Exception e) {
            log.error("Error generating travel plan", e);
            throw new RuntimeException("Failed to generate travel plan: " + e.getMessage(), e);
        }
    }

    public String generateTravelTips(String destination) {
        try {
            String prompt = String.format(
                "Provide 5 essential travel tips for visiting %s. " +
                "Include practical advice about local customs, safety, best time to visit, " +
                "transportation, and money-saving tips.",
                destination
            );

            return getChatCompletion(prompt);
        } catch (Exception e) {
            log.error("Error generating travel tips", e);
            throw new RuntimeException("Failed to generate travel tips: " + e.getMessage(), e);
        }
    }

    public String suggestDestinations(String preferences) {
        try {
            String prompt = String.format(
                "Based on these travel preferences: '%s', " +
                "suggest 5 ideal destinations with a brief explanation of why each would be a good match.",
                preferences
            );

            return getChatCompletion(prompt);
        } catch (Exception e) {
            log.error("Error suggesting destinations", e);
            throw new RuntimeException("Failed to suggest destinations: " + e.getMessage(), e);
        }
    }

    private String buildPrompt(TravelRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Create a detailed travel itinerary for a trip with the following details:\n\n");
        prompt.append("Destination: ").append(request.getDestination()).append("\n");
        prompt.append("Duration: ").append(request.getNumberOfDays()).append(" days\n");

        if (request.getInterests() != null && !request.getInterests().isEmpty()) {
            prompt.append("Interests: ").append(request.getInterests()).append("\n");
        }

        if (request.getBudget() != null && !request.getBudget().isEmpty()) {
            prompt.append("Budget: ").append(request.getBudget()).append("\n");
        }

        if (request.getTravelStyle() != null && !request.getTravelStyle().isEmpty()) {
            prompt.append("Travel Style: ").append(request.getTravelStyle()).append("\n");
        }

        prompt.append("\nPlease provide:\n");
        prompt.append("1. A brief overview of the trip\n");
        prompt.append("2. A day-by-day itinerary with specific activities, times, and locations\n");
        prompt.append("3. General recommendations (what to pack, local tips, etc.)\n");
        prompt.append("4. Estimated budget breakdown\n");
        prompt.append("\nFormat the response in a clear, structured way with day-by-day details.");

        return prompt.toString();
    }

    private String getChatCompletion(String prompt) {
        try {
            ChatCompletionService chatService = kernel.getService(ChatCompletionService.class);

            ChatHistory chatHistory = new ChatHistory();
            chatHistory.addUserMessage(prompt);

            var result = chatService.getChatMessageContentsAsync(
                chatHistory,
                kernel,
                null
            ).block();

            if (result != null && !result.isEmpty()) {
                return result.get(0).getContent();
            }

            throw new RuntimeException("No response from AI service");
        } catch (Exception e) {
            log.error("Error getting chat completion", e);
            throw new RuntimeException("Failed to get chat completion: " + e.getMessage(), e);
        }
    }

    private TravelPlan parseTravelPlan(String aiResponse, TravelRequest request) {
        // Since the AI response is free-form text, we'll create a simplified TravelPlan
        // In a production system, you might want to use structured output or additional parsing

        return TravelPlan.builder()
                .destination(request.getDestination())
                .numberOfDays(request.getNumberOfDays())
                .overview(aiResponse)
                .build();
    }
}
