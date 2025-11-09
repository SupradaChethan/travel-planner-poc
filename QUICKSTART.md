# Quick Start Guide

## 🚀 Getting Started in 3 Steps

### Step 1: Set Your API Key
```bash
export OPENAI_API_KEY=your-openai-api-key-here
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```

### Step 3: Open the Web UI
Open your browser to: **http://localhost:8080**

---

## 🎯 Try These Examples

### Example 1: Weekend in Paris
- **Destination**: Paris
- **Days**: 3
- **Interests**: culture, food, art
- **Budget**: moderate
- **Style**: balanced

### Example 2: Adventure in Tokyo
- **Destination**: Tokyo
- **Days**: 5
- **Interests**: technology, food, traditional culture
- **Budget**: luxury
- **Style**: packed

### Example 3: Get Travel Tips
Navigate to the **Travel Tips** tab and enter:
- **Destination**: Barcelona

### Example 4: Find Destinations
Navigate to the **Suggestions** tab and enter:
- **Preferences**: "I love beaches, warm weather, great food, and want something budget-friendly"

---

## 📝 First Time Setup

If you don't have Maven installed:

**macOS:**
```bash
brew install maven
```

**Windows (with Chocolatey):**
```bash
choco install maven
```

**Linux:**
```bash
sudo apt-get install maven
```

---

## 🔧 Troubleshooting

### Issue: Port 8080 already in use
Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Issue: API Key not working
Make sure your OpenAI API key is valid and has credits available. Check at https://platform.openai.com/account/billing

### Issue: Slow responses
Consider using `gpt-3.5-turbo` instead of `gpt-4` in `application.properties`:
```properties
openai.model=gpt-3.5-turbo
```

---

## 🎨 UI Features

- **Responsive Design**: Works on desktop, tablet, and mobile
- **Real-time Loading States**: Visual feedback while AI generates responses
- **Toast Notifications**: Success and error messages
- **Smooth Animations**: Polished user experience
- **Tab Navigation**: Easy switching between different features

---

## 🌟 Tips for Best Results

1. **Be Specific**: The more details you provide, the better the AI can personalize your itinerary
2. **Interests Matter**: Mention specific interests (e.g., "street food" vs just "food")
3. **Budget Considerations**: Specify budget to get appropriate recommendations
4. **Travel Style**: Choose the pace that matches your preference

Enjoy planning your next adventure! ✈️
