package com.sinontech.study.dao;

import com.sinontech.study.entity.ChatMessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageDao {
    void insert(ChatMessageEntity message);
    void deleteBySessionId(String sessionId);
    List<ChatMessageEntity> findBySessionId(String sessionId);
}
