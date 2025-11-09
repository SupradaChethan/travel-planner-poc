package com.travel.planner.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.KeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelConfig {

    private static final Logger log = LoggerFactory.getLogger(SemanticKernelConfig.class);

    @Value("${ai.provider:openai}")
    private String aiProvider;

    // OpenAI Configuration
    @Value("${openai.api.key:}")
    private String openAiApiKey;

    @Value("${openai.model:gpt-4}")
    private String openAiModel;

    // Azure OpenAI Configuration
    @Value("${azure.openai.endpoint:}")
    private String azureOpenAiEndpoint;

    @Value("${azure.openai.api.key:}")
    private String azureOpenAiApiKey;

    @Value("${azure.openai.deployment.name:}")
    private String azureDeploymentName;

    @Bean
    public OpenAIAsyncClient openAIAsyncClient() {
        if ("azure".equalsIgnoreCase(aiProvider)) {
            log.info("Configuring Azure OpenAI with endpoint: {}", azureOpenAiEndpoint);
            return new OpenAIClientBuilder()
                    .endpoint(azureOpenAiEndpoint)
                    .credential(new AzureKeyCredential(azureOpenAiApiKey))
                    .buildAsyncClient();
        } else {
            log.info("Configuring OpenAI with model: {}", openAiModel);
            return new OpenAIClientBuilder()
                    .credential(new KeyCredential(openAiApiKey))
                    .buildAsyncClient();
        }
    }

    @Bean
    public ChatCompletionService chatCompletionService(OpenAIAsyncClient client) {
        String modelId = "azure".equalsIgnoreCase(aiProvider) ? azureDeploymentName : openAiModel;

        return OpenAIChatCompletion.builder()
                .withModelId(modelId)
                .withOpenAIAsyncClient(client)
                .build();
    }

    @Bean
    public Kernel kernel(ChatCompletionService chatCompletionService) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .build();
    }
}
