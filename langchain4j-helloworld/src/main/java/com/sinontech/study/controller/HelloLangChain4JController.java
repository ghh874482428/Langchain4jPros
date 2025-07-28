package com.sinontech.study.controller;


import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloLangChain4JController {

    @Resource
    private ChatModel chatModel;
    //http://localhost:9001/langchain4j/hello?question=如何学习java
    @GetMapping("/langchain4j/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "你是谁") String name){
        log.info("name:{}",name);
        String result = chatModel.chat(name);
        log.info("调用大模型回复:{}",result);
        return result;
    }
}
