package com.sinontech.study.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageEntity {
    private Long id;
    private String sessionId;
    private String content;
    private String role; // USER, AI, SYSTEM
    private Integer messageIndex;
    private Date createdAt;
}
