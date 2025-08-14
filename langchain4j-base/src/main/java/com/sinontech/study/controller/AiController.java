package com.sinontech.study.controller;

import com.sinontech.study.enums.ModelType;
import com.sinontech.study.service.AiService;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestParam String sessionId,
            @RequestParam String message,
            @RequestParam(required = false) ModelType model) {
        return ResponseEntity.ok(aiService.chat(sessionId, message, model));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(
            @RequestParam String sessionId,
            @RequestParam String message,
            @RequestParam(required = false) ModelType model) {
        return aiService.streamChat(sessionId, message, model);
    }

    @PostMapping("/image")
    public ResponseEntity<byte[]> generateImage(@RequestBody String prompt) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(aiService.generateImage(prompt));
    }

    @PostMapping("/audio")
    public ResponseEntity<byte[]> generateAudio(@RequestBody String text) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"audio.wav\"")
                .body(aiService.generateAudio(text));
    }
}
