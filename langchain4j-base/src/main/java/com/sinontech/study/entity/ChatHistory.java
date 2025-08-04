package com.sinontech.study.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatHistory {
    private Long id;
    private String sessionId;
    private String userMessage;
    private String aiMessage;
    private String modelType;
    private Date createdAt;
}
