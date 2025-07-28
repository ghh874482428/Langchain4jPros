package com.sinontech.study.config;

import com.sinontech.study.config.redis.RedisChatMemoryStore;
import com.sinontech.study.service.lc4j.RegularChatAssistant;
import com.sinontech.study.service.lc4j.StreamingChatAssistant;
import com.sinontech.study.tool.RingOrderTool;
import jakarta.annotation.Resource;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import com.sinontech.study.config.llmfactory.ModelFactory;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig
{
    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private ModelFactory modelFactory;

    @Resource
    private RingOrderTool ringOrderTool;

    /**
     * 创建聊天模型实例
     * @return ChatModel实例，使用"qwen"模型配置
     */
    @Bean
    public ChatModel chatModel()
    {
        return modelFactory.createChatModel("qwen");
    }

    /**
     * 创建带内存的常规聊天助手
     * @param chatModel 聊天模型实例
     * @return 配置了Redis内存存储的RegularChatAssistant实例
     */
    @Bean(name = "regularChatAssistant")
    public RegularChatAssistant chatMemoryAssistant(ChatModel chatModel)
    {

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)
                .chatMemoryStore(redisChatMemoryStore)
                .build();

        return AiServices.builder(RegularChatAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(ringOrderTool)
                .build();
    }

    /**
     * 创建流式聊天模型实例
     * @return StreamingChatModel实例，使用"qwen"模型配置
     */
    @Bean
    public StreamingChatModel streamingChatModel(){
        return modelFactory.createStreamingChatModel("qwen");
    }

    /**
     * 创建带内存的流式聊天助手
     * @param streamingChatModel 流式聊天模型实例
     * @return 配置了Redis内存存储的StreamingChatAssistant实例
     */
    @Bean(name = "streamingChatAssistant")
    public StreamingChatAssistant chatAssistant(StreamingChatModel streamingChatModel){
        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)
                .chatMemoryStore(redisChatMemoryStore)
                .build();

        return AiServices.builder(StreamingChatAssistant.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(ringOrderTool)
                .build();
    }

}
