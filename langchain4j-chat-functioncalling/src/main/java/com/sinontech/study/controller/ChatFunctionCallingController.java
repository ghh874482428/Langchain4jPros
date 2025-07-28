package com.sinontech.study.controller;

import cn.hutool.core.date.DateUtil;
import com.sinontech.study.service.FunctionAssistant;
import com.sinontech.study.service.FunctionAssistantHigh;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ChatFunctionCallingController
{
    @Resource
    private FunctionAssistant functionAssistant;


    @Resource
    private FunctionAssistantHigh functionAssistantHigh;


    @GetMapping(value = "/chatfunction/test1")
    public String test1()
    {
        String chat = functionAssistant.chat("开张发票,公司：浙江欣网科技有限公司 税号：xxxxxx 金额：888");

        System.out.println(chat);

        return "success : "+ DateUtil.now() + "\t"+chat;
    }


    @GetMapping(value = "/chatfunction/test2")
    public String test2()
    {
        String chat = functionAssistantHigh.chat("开张发票,公司：浙江欣网科技有限公司 税号：123465 金额：999");

        System.out.println(chat);

        return "success : "+ DateUtil.now() + "\t"+chat;
    }
}
