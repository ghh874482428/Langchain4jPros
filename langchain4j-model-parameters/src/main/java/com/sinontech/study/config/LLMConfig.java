package com.sinontech.study.config;


import com.sinontech.study.listener.TestChatModelListener;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

//https://docs.langchain4j.dev/tutorials/model-parameters/
@Configuration
public class LLMConfig {

    @Bean("qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true) // 日志级别设置为debug才有效
                .logResponses(true)// 日志级别设置为debug才有效
                .listeners(List.of(new TestChatModelListener()))
                .maxRetries(2)
                .timeout(Duration.ofSeconds(2))//向大模型发送请求时，如在指定时间内没有收到响应，该请求将被中断并报request timed out
                .build();
    }

}
