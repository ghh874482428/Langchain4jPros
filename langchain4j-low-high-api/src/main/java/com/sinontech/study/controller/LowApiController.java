package com.sinontech.study.controller;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LowApiController {

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepseek;


    @GetMapping(value = "/lc4j/boot/api01")
    public String api01(@RequestParam(name = "prompt",defaultValue = "你是谁") String prompt){
        log.info("prompt:{}",prompt);
        String response = chatModelQwen.chat(prompt);
        log.info("response:{}",response);
        return response;
    }

    @GetMapping(value = "/lc4j/boot/api02")
    public String api02(@RequestParam(name="prompt",defaultValue = "你是谁") String prompt){
        log.info("prompt:{}",prompt);
        ChatResponse chat = chatModelDeepseek.chat(UserMessage.from(prompt));
        String text = chat.aiMessage().text();
        log.info("response:{}",text);
        TokenUsage tokenUsage = chat.tokenUsage();
        log.info("tokenUsage:{}",tokenUsage);
        return text;
    }

}
