package com.sinontech.study.config.llmfactory;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
                .logRequests(true) // 日志级别设置为debug才有效
                .logResponses(true)// 日志级别设置为debug才有效
                .timeout(Duration.ofSeconds(30))//延长超时时间至30秒，避免请求超时
                .build();
    }

    public StreamingChatModel createStreamingChatModel(String modelType) {
        ModelProperties.ModelConfig config = modelProperties.getModels().get(modelType);
        return OpenAiStreamingChatModel.builder()
                .apiKey(config.getApiKey())
                .modelName(config.getModelName())
                .baseUrl(config.getBaseUrl())
                .logRequests(true) // 日志级别设置为debug才有效
                .logResponses(true)// 日志级别设置为debug才有效
                .timeout(Duration.ofSeconds(30))//向大模型发送请求时，如在指定时间内没有收到响应，该请求将被中断并报request timed out
                .build();
    }
}
