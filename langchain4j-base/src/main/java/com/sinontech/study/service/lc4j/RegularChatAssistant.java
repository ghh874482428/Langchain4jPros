package com.sinontech.study.service.lc4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RegularChatAssistant {

    @SystemMessage(fromResource = "/system-message.yml")
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
