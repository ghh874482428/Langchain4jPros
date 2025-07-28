package com.sinontech.study.config.llmfactory;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelFactory {

    private final ModelProperties modelProperties;

    @Autowired
    public ModelFactory(ModelProperties modelProperties) {
        this.modelProperties = modelProperties;
    }

    public ChatModel createChatModel(String modelType) {
        ModelProperties.ModelConfig config = modelProperties.getModels().get(modelType);
        return OpenAiChatModel.builder()
                .apiKey(config.getApiKey())
                .modelName(config.getModelName())
                .baseUrl(config.getBaseUrl())
                .build();
    }

    public StreamingChatModel createStreamingChatModel(String modelType) {
        ModelProperties.ModelConfig config = modelProperties.getModels().get(modelType);
        return OpenAiStreamingChatModel.builder()
                .apiKey(config.getApiKey())
                .modelName(config.getModelName())
                .baseUrl(config.getBaseUrl())
                .build();
    }
}
