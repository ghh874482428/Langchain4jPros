package com.sinontech.study.controller;


import com.sinontech.study.service.ChatAssistant;
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
public class HighApiController {

    @Resource
    private ChatAssistant chatAssistant;


    @GetMapping(value = "/lc4j/boot/highapi01")
    public String highApi01(@RequestParam(name = "prompt",defaultValue = "你是谁") String prompt){
        log.info("prompt:{}",prompt);
        String response = chatAssistant.chat(prompt);
        log.info("response:{}",response);
        return response;
    }
}
