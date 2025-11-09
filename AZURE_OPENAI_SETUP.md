# Azure OpenAI Setup Guide

This guide explains how to configure the Travel Planner application to use Azure OpenAI instead of OpenAI.

## Why Azure OpenAI?

- **Enterprise-ready**: Better compliance, security, and SLA guarantees
- **Private deployment**: Your data stays within your Azure subscription
- **Regional availability**: Deploy closer to your users
- **Integration**: Works seamlessly with other Azure services

## Prerequisites

1. **Azure Subscription**: You need an active Azure subscription
2. **Azure OpenAI Resource**: Create an Azure OpenAI service in Azure Portal
3. **Model Deployment**: Deploy a GPT model (e.g., GPT-4, GPT-3.5-turbo)

## Step-by-Step Setup

### 1. Create Azure OpenAI Resource

1. Go to [Azure Portal](https://portal.azure.com)
2. Click "Create a resource"
3. Search for "Azure OpenAI"
4. Click "Create"
5. Fill in the required information:
   - **Subscription**: Select your subscription
   - **Resource group**: Create new or select existing
   - **Region**: Choose a region (e.g., East US, West Europe)
   - **Name**: Give your resource a unique name (e.g., `my-travel-planner-openai`)
   - **Pricing tier**: Select appropriate tier
6. Click "Review + create" then "Create"

### 2. Deploy a Model

1. Once the resource is created, go to the resource
2. Click "Model deployments" or go to [Azure OpenAI Studio](https://oai.azure.com/)
3. Click "Create new deployment"
4. Select a model:
   - **GPT-4**: Best quality, slower, more expensive
   - **GPT-3.5-turbo**: Fast, cost-effective
5. Give your deployment a name (e.g., `gpt-4-travel`)
   - **Important**: Remember this deployment name!
6. Click "Create"

### 3. Get Your Credentials

1. In Azure Portal, go to your OpenAI resource
2. Click "Keys and Endpoint" in the left menu
3. Copy the following:
   - **Endpoint**: Something like `https://your-resource-name.openai.azure.com/`
   - **Key 1** or **Key 2**: Your API key

### 4. Configure the Application

You have three options to configure:

#### Option A: Environment Variables (Recommended)

```bash
export AZURE_OPENAI_ENDPOINT=https://your-resource-name.openai.azure.com/
export AZURE_OPENAI_API_KEY=your-azure-api-key-here
export AZURE_OPENAI_DEPLOYMENT_NAME=gpt-4-travel
```

Then run:
```bash
mvn spring-boot:run
```

#### Option B: Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# Set AI provider to Azure
ai.provider=azure

# Azure OpenAI Configuration
azure.openai.endpoint=https://your-resource-name.openai.azure.com/
azure.openai.api.key=your-azure-api-key-here
azure.openai.deployment.name=gpt-4-travel
```

#### Option C: Use Azure Profile

Edit `src/main/resources/application-azure.properties` with your values, then run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=azure
```

### 5. Verify Configuration

1. Start the application
2. Check the logs for:
   ```
   Configuring Azure OpenAI with endpoint: https://your-resource-name.openai.azure.com/
   ```
3. Open the browser to `http://localhost:8080`
4. Try generating a travel plan

## Configuration Reference

### Required Properties

| Property | Description | Example |
|----------|-------------|---------|
| `ai.provider` | AI service provider | `azure` |
| `azure.openai.endpoint` | Your Azure OpenAI endpoint | `https://my-openai.openai.azure.com/` |
| `azure.openai.api.key` | Your Azure OpenAI API key | `abc123...` |
| `azure.openai.deployment.name` | Your deployment name (NOT model name) | `gpt-4-travel` |

### Common Deployment Names

When you create a deployment in Azure, you choose the deployment name:

- `gpt-4` - If you named your GPT-4 deployment "gpt-4"
- `gpt-4-travel` - Custom name for travel-specific deployment
- `gpt-35-turbo` - Common name for GPT-3.5-turbo deployment

**Note**: Use your deployment name, not the model name!

## Troubleshooting

### Error: "404 - Deployment not found"
- **Issue**: Wrong deployment name
- **Solution**: Check your deployment name in Azure Portal or Azure OpenAI Studio

### Error: "401 - Unauthorized"
- **Issue**: Invalid API key or endpoint
- **Solution**: Verify your API key and endpoint from Azure Portal

### Error: "403 - Forbidden"
- **Issue**: Region or subscription not approved for Azure OpenAI
- **Solution**: Request access via [Azure OpenAI Access Form](https://aka.ms/oai/access)

### Error: "429 - Rate limit exceeded"
- **Issue**: Too many requests
- **Solution**:
  - Wait and retry
  - Increase quota in Azure Portal
  - Upgrade pricing tier

### Application says "Using OpenAI" instead of "Azure OpenAI"
- **Issue**: `ai.provider` not set to `azure`
- **Solution**: Check `application.properties` has `ai.provider=azure`

## Cost Management

Azure OpenAI pricing is based on:
- **Token usage**: Pay per 1K tokens
- **Model type**: GPT-4 is more expensive than GPT-3.5-turbo
- **Region**: Prices may vary by region

### Tips to Reduce Costs

1. Use GPT-3.5-turbo for development and testing
2. Set quota limits in Azure Portal
3. Monitor usage in Azure Cost Management
4. Use shorter prompts when possible

## Security Best Practices

1. **Never commit API keys**: Use environment variables
2. **Rotate keys regularly**: Use Key Vault for production
3. **Enable RBAC**: Use Azure AD authentication instead of keys
4. **Network security**: Use Private Endpoints for production
5. **Monitor access**: Enable diagnostic logs

## Switching Between OpenAI and Azure OpenAI

You can easily switch between providers:

1. **To use OpenAI**:
   ```properties
   ai.provider=openai
   ```

2. **To use Azure OpenAI**:
   ```properties
   ai.provider=azure
   ```

The application will automatically use the correct configuration!

## Additional Resources

- [Azure OpenAI Documentation](https://learn.microsoft.com/azure/cognitive-services/openai/)
- [Azure OpenAI Pricing](https://azure.microsoft.com/pricing/details/cognitive-services/openai-service/)
- [Microsoft Semantic Kernel Documentation](https://learn.microsoft.com/semantic-kernel/)
- [Request Azure OpenAI Access](https://aka.ms/oai/access)

## Need Help?

If you encounter issues:
1. Check the application logs for detailed error messages
2. Verify your Azure OpenAI deployment is active
3. Ensure your subscription has quota available
4. Review the troubleshooting section above
