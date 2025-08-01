package com.sinontech.study.config.redis;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisChatMemoryStore implements ChatMemoryStore
{

    public static final String CHAT_MEMORY_PREFIX = "AI:CHAT_MEMORY:";

    @Resource
    private RedisTemplate<String,String> redisTemplate;



    @Override
    public List<ChatMessage> getMessages(Object memoryId)
    {
        String retValue = redisTemplate.opsForValue().get(CHAT_MEMORY_PREFIX + memoryId);

        return  ChatMessageDeserializer.messagesFromJson(retValue);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages)
    {
        redisTemplate.opsForValue()
                .set(CHAT_MEMORY_PREFIX + memoryId, ChatMessageSerializer.messagesToJson(messages));
    }

    @Override
    public void deleteMessages(Object memoryId)
    {
        redisTemplate.delete(CHAT_MEMORY_PREFIX + memoryId);
    }
}
