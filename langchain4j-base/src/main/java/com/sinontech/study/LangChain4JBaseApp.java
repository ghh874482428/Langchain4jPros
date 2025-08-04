package com.sinontech.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 将客户和大模型的对话问答保存进Redis进行持久化记忆留存
 * 知识出处,https://docs.langchain4j.dev/tutorials/chat-memory#persistence
 */
@SpringBootApplication
@MapperScan("com.sinontech.study.dao")
public class LangChain4JBaseApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(LangChain4JBaseApp.class,args);
    }
}
