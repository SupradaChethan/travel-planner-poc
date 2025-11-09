# Travel Planner AI

A Spring Boot application that uses Microsoft Semantic Kernel to create intelligent travel itineraries and provide travel recommendations.

## Features

- **Beautiful Web UI** - Modern, responsive interface for planning your trips
- **Generate Travel Plans** - Create detailed itineraries based on destination, duration, and preferences
- **Travel Tips** - Get essential tips for specific destinations
- **Destination Suggestions** - Receive personalized recommendations based on your interests
- **AI-Powered** - Uses OpenAI GPT models through Microsoft Semantic Kernel

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- **Either** OpenAI API Key **or** Azure OpenAI Service

## Setup

1. **Clone the repository** (or use the current directory)

2. **Configure your AI Provider**

   The application supports both **OpenAI** and **Azure OpenAI**. Choose one:

   ### Option A: Using OpenAI (Default)

   Set as environment variable:
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   ```

   Or update `src/main/resources/application.properties`:
   ```properties
   ai.provider=openai
   openai.api.key=your-api-key-here
   openai.model=gpt-4
   ```

   ### Option B: Using Azure OpenAI

   Set environment variables:
   ```bash
   export AZURE_OPENAI_ENDPOINT=https://your-resource-name.openai.azure.com/
   export AZURE_OPENAI_API_KEY=your-azure-api-key
   export AZURE_OPENAI_DEPLOYMENT_NAME=your-deployment-name
   ```

   Or update `src/main/resources/application.properties`:
   ```properties
   ai.provider=azure
   azure.openai.endpoint=https://your-resource-name.openai.azure.com/
   azure.openai.api.key=your-azure-api-key
   azure.openai.deployment.name=your-deployment-name
   ```

   **Quick Start with Azure Profile:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=azure
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

5. **Open the Web UI**

   Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

   You'll see a beautiful interface with three tabs:
   - **Travel Plan**: Create detailed itineraries
   - **Travel Tips**: Get destination-specific advice
   - **Suggestions**: Discover new destinations based on preferences

## Using the Web UI

The application includes a modern web interface accessible at `http://localhost:8080` after starting the server.

### Travel Plan Tab
1. Enter your destination (e.g., "Paris", "Tokyo")
2. Specify number of days
3. Optionally add interests, budget level, and travel style
4. Click "Generate Travel Plan" to get your AI-powered itinerary

### Travel Tips Tab
1. Enter a destination
2. Click "Get Travel Tips" to receive essential advice about customs, safety, transportation, and more

### Suggestions Tab
1. Describe your travel preferences (e.g., "beaches, warm weather, good food")
2. Click "Get Suggestions" to receive personalized destination recommendations

## API Endpoints

The application also provides REST API endpoints for programmatic access:

### 1. Generate Travel Plan

**POST** `/api/travel/plan`

Creates a detailed travel itinerary based on your preferences.

**Request Body:**
```json
{
  "destination": "Paris",
  "numberOfDays": 3,
  "interests": "culture, food, art",
  "budget": "moderate",
  "travelStyle": "balanced"
}
```

**Response:**
```json
{
  "destination": "Paris",
  "numberOfDays": 3,
  "overview": "Detailed day-by-day itinerary with activities, times, and recommendations..."
}
```

### 2. Get Travel Tips

**GET** `/api/travel/tips/{destination}`

Get essential travel tips for a specific destination.

**Example:**
```bash
curl http://localhost:8080/api/travel/tips/Tokyo
```

**Response:**
```json
{
  "destination": "Tokyo",
  "tips": "1. Best time to visit...\n2. Local customs...\n..."
}
```

### 3. Suggest Destinations

**POST** `/api/travel/suggest`

Get destination suggestions based on your preferences.

**Request Body:**
```json
{
  "preferences": "beaches, warm weather, good food, budget-friendly"
}
```

**Response:**
```json
{
  "suggestions": "1. Bali, Indonesia - ...\n2. Thailand - ...\n..."
}
```

### 4. Health Check

**GET** `/api/travel/health`

Check if the service is running.

```bash
curl http://localhost:8080/api/travel/health
```

## Testing with cURL

### Generate a Travel Plan
```bash
curl -X POST http://localhost:8080/api/travel/plan \
  -H "Content-Type: application/json" \
  -d '{
    "destination": "Barcelona",
    "numberOfDays": 4,
    "interests": "architecture, food, beaches",
    "budget": "moderate",
    "travelStyle": "balanced"
  }'
```

### Get Travel Tips
```bash
curl http://localhost:8080/api/travel/tips/London
```

### Suggest Destinations
```bash
curl -X POST http://localhost:8080/api/travel/suggest \
  -H "Content-Type: application/json" \
  -d '{
    "preferences": "mountains, hiking, nature, adventure"
  }'
```

## Configuration

Edit `src/main/resources/application.properties` to customize:

- **Server Port**: `server.port=8080`
- **OpenAI Model**: `openai.model=gpt-4` (or `gpt-3.5-turbo` for faster, cheaper responses)
- **Logging Level**: `logging.level.com.travel.planner=INFO`

## Technology Stack

- **Spring Boot 3.2.0** - Application framework
- **Microsoft Semantic Kernel 1.2.0** - AI orchestration
- **OpenAI GPT** - Language model for generating travel content
- **Lombok** - Reduce boilerplate code
- **Maven** - Build and dependency management

## Project Structure

```
src/
├── main/
│   ├── java/com/travel/planner/
│   │   ├── config/
│   │   │   ├── SemanticKernelConfig.java
│   │   │   └── WebConfig.java
│   │   ├── controller/
│   │   │   └── TravelPlannerController.java
│   │   ├── model/
│   │   │   ├── Activity.java
│   │   │   ├── DayItinerary.java
│   │   │   ├── TravelPlan.java
│   │   │   └── TravelRequest.java
│   │   ├── service/
│   │   │   └── TravelPlannerService.java
│   │   └── TravelPlannerApplication.java
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   │   └── style.css
│       │   ├── js/
│       │   │   └── app.js
│       │   └── index.html
│       ├── application.properties
│       └── application-dev.properties
└── test/
    └── java/com/travel/planner/
```

## Future Enhancements

- Add structured JSON parsing for better itinerary formatting
- Implement caching for frequently requested destinations
- Add user preferences persistence
- Include weather data integration
- Add hotel and flight recommendations
- Support for multiple languages
- Export itineraries to PDF/Calendar formats

## Troubleshooting

### Issue: Application fails to start
- Ensure Java 17+ is installed: `java -version`
- Verify Maven is properly configured: `mvn -version`

### Issue: AI responses are slow
- Consider switching to `gpt-3.5-turbo` model in `application.properties`
- Check your internet connection

### Issue: API Key errors
- Verify your OpenAI API key is correctly set
- Check that you have sufficient credits in your OpenAI account

## License

MIT License
