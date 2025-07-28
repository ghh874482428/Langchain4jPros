package com.sinontech.study.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 知识出处 https://docs.langchain4j.dev/get-started
 */
@Configuration
public class LLMConfig {

    /**
     * https://docs.langchain4j.dev/get-started
     */
    @Bean(name = "qwen")
    @Primary
    public ChatModel chatModelQwen(){
        String getenv = System.getenv("aliQwen-api");
        return OpenAiChatModel.builder()
                .apiKey(getenv)
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }


    /**
     * 知识出处，https://api-docs.deepseek.com/zh-cn/
     */
    @Bean(name = "deepseek")
    public ChatModel chatModelDeepSeek()
    {
        return
                OpenAiChatModel.builder()
                        .apiKey(System.getenv("deepseek-api"))
                        .modelName("deepseek-chat")
                        //.modelName("deepseek-reasoner")
                        .baseUrl("https://api.deepseek.com/v1")
                        .build();
    }
}
