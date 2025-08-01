package com.sinontech.study.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 模型参数
// https://docs.langchain4j.dev/tutorials/model-parameters/
@RestController
@Slf4j
public class ModelParameterController
{
    @Resource
    private ChatModel chatModelQwen;

    @GetMapping(value = "/modelparam/config")
    public String config(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        String result = chatModelQwen.chat(prompt);
        System.out.println("通过langchain4j调用模型返回结果："+result);
        return result;
    }
}
