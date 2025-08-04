package com.sinontech.study.dao;

import com.sinontech.study.entity.ChatHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatHistoryDao {
    void insert(ChatHistory chatHistory);
}
