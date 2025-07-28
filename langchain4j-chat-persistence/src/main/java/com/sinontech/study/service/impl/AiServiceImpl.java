package com.sinontech.study.service.impl;

import com.sinontech.study.dao.ChatHistoryDao;
import com.sinontech.study.entity.ChatHistory;
import com.sinontech.study.enums.ModelType;
import com.sinontech.study.service.AiService;
import com.sinontech.study.service.lc4j.RegularChatAssistant;
import com.sinontech.study.service.lc4j.StreamingChatAssistant;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    @Resource
    private RegularChatAssistant regularChatAssistant;

    @Resource
    private StreamingChatAssistant streamingChatAssistant;

    private final ChatHistoryDao chatHistoryDao;

    @Override
    public String chat(String sessionId, String message, ModelType modelType) {
        String response = regularChatAssistant.chat(sessionId, message);
        // 保存完整对话历史
        saveChatHistory(sessionId, message, response, modelType);
        return response;
    }

    @Override
    public Flux<String> streamChat(String sessionId, String message, ModelType modelType) {
        // 收集流式响应
        StringBuilder fullResponse = new StringBuilder();
        return streamingChatAssistant.chatFlux(sessionId, message)
                .doOnNext(fullResponse::append)
                .doOnComplete(() -> saveChatHistory(sessionId, message, fullResponse.toString(), modelType));
    }

    @Override
    public byte[] generateImage(String prompt) {
        return new byte[0];
    }

    @Override
    public byte[] generateAudio(String text) {
        return new byte[0];
    }


    private void saveChatHistory(String sessionId, String userMsg, String aiMsg, ModelType modelType) {
        ChatHistory history = new ChatHistory();
        history.setSessionId(sessionId);
        history.setUserMessage(userMsg);
        history.setAiMessage(aiMsg);
        history.setModelType(modelType != null ? modelType.name() : "DEFAULT");
        chatHistoryDao.insert(history);
    }
}
