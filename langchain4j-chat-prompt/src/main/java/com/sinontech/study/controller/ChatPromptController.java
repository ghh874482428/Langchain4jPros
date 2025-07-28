package com.sinontech.study.controller;

import cn.hutool.core.date.DateUtil;
import com.sinontech.study.entities.LawPrompt;
import com.sinontech.study.service.LawAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Slf4j
public class ChatPromptController
{
    @Resource
    private LawAssistant lawAssistant;
    @Resource
    private ChatModel chatModel;

    @GetMapping(value = "/chatprompt/test1")
    public String test1()
    {
        String chat = lawAssistant.chat("什么是java？",50,"json");
        System.out.println(chat);

        String chat3 = lawAssistant.chat("介绍下西瓜和芒果",50,"json");
        System.out.println(chat3);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chat+"<br> \n\n chat2: "+chat3;
    }

    /**
     * @return
     */
    @GetMapping(value = "/chatprompt/test2")
    public String test2()
    {
        LawPrompt prompt = new LawPrompt();

        prompt.setLegal("多线程");
        prompt.setQuestion("怎么快速处理100W数据?");
        prompt.setFormat("json");

        String chat = lawAssistant.chat(prompt);

        System.out.println(chat);

        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chat;
    }


    /**
    * @Description: 单个参数可以使用{{it}》”占位符或者”{{参数名}”，如果为其他字符，系统不能自动识别会报错。
    */
    @GetMapping(value = "/chatprompt/test3")
    public String test3()
    {
        // 看看源码，默认 PromptTemplate 构造使用 it 属性作为默认占位符

        /*String role = "外科医生";
        String question = "牙疼";*/

        String role = "财务会计";
        String question = "人民币大写";

        //1 构造PromptTemplate模板
        PromptTemplate template = PromptTemplate.from("你是一个{{it}}助手,{{question}}怎么办");
        //2 由PromptTemplate生成Prompt
        Prompt prompt = template.apply(Map.of("it",role,"question",question));
        //3 Prompt提示词变成UserMessage
        UserMessage userMessage = prompt.toUserMessage();
        //4 调用大模型
        ChatResponse chatResponse = chatModel.chat(userMessage);

        //4.1 后台打印
        System.out.println(chatResponse.aiMessage().text());
        //4.2 前台返回
        return "success : "+ DateUtil.now()+"<br> \n\n chat: "+chatResponse.aiMessage().text();
    }
}
