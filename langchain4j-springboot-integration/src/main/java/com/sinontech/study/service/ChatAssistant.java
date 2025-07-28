package com.sinontech.study.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatAssistant {
    @SystemMessage("你是一名java后端")
    String chat(String userMessage);
}
