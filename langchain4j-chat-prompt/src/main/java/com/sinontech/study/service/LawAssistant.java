package com.sinontech.study.service;

import com.sinontech.study.entities.LawPrompt;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * @Description: TODO
 */
public interface LawAssistant
{
    //案例1 @SystemMessage+@UserMessage+@V
    @SystemMessage("你是一位专业的JAVA高级工程师问答机器人，只回答与JAVA相关的问题。" +
            "输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答JAVA相关的问题。'")

    @UserMessage("请回答以下JAVA问题：{{question}},字数控制在{{length}}以内,输出格式为{{format}}")

    String chat(@V("question") String question, @V("length") int length,@V("format") String format);


    //案例2 新建带着@StructuredPrompt的业务实体类，比如LawPrompt
    @SystemMessage("你是一位专业的JAVA高级工程师，只回答与JAVA相关的问题。" +
            "输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答JAVA相关的问题。'")
    String chat(LawPrompt lawPrompt);
}
