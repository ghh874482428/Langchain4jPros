package com.sinontech.study.controller;


import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TogetherController {

    @Resource(name="qwen")
    private ChatModel chatModelQwen;

    @Resource(name="deepseek")
    private ChatModel chatModelDeepSeek;

    @GetMapping("/together/qwen")
    public String qwenCall(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("/together/qwen>>>>>>>>>>prompt:{}", prompt);
        try {
            String answer = chatModelQwen.chat(prompt);
            log.info("/together/qwen>>>>>>>>>>answer:{}", answer);
            return answer;
        } catch (Exception e) {
            log.error("/together/qwen>>>>>>>>>>error:{}", e.getMessage());
            return "An error occurred while processing your request.";
        }
    }



    @GetMapping("/together/deepseek")
    public String deepseekCall(@RequestParam(value = "prompt",defaultValue = "你是谁") String prompt) {
        log.info("/together/deepseek>>>>>>>>>>prompt:{}",prompt);
        String answer = chatModelDeepSeek.chat(prompt);
        log.info("/together/deepseek>>>>>>>>>>answer:{}",answer);
        return answer;
    }


}
