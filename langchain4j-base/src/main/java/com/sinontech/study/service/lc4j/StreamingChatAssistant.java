package com.sinontech.study.service.lc4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface StreamingChatAssistant {


    @SystemMessage(fromResource = "/system-message.yml")
    Flux<String> chatFlux(@MemoryId String sessionId, @UserMessage String userMessage);
}
