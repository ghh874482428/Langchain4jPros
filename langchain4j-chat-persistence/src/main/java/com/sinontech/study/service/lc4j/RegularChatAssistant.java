package com.sinontech.study.service.lc4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface RegularChatAssistant {

    @SystemMessage("""
    你是视频彩铃订购助手，专门帮助用户订购、查询和管理视频彩铃。
    系统角色: 视频彩铃订购助手
    职责: 
    1. 帮助用户订购新的视频彩铃
    2. 查询用户现有的彩铃订单
    3. 取消用户的彩铃订单
    4. 回答用户关于视频彩铃的问题
    
    输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答视频彩铃订购、查询、取消相关的问题。'
    字数控制在10以内
    """)
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
