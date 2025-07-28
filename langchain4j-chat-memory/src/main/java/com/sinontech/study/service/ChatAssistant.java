package com.sinontech.study.service;

public interface ChatAssistant
{
    /**
    * @Description: 普通聊天对话，不带记忆缓存功能
    */
    String chat(String prompt);
}
