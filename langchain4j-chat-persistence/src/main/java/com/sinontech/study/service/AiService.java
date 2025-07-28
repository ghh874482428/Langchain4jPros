package com.sinontech.study.service;

import com.sinontech.study.enums.ModelType;
import reactor.core.publisher.Flux;

public interface AiService {
    String chat(String sessionId, String message, ModelType modelType);
    Flux<String> streamChat(String sessionId, String message, ModelType modelType);
    byte[] generateImage(String prompt);
    byte[] generateAudio(String text);
}
