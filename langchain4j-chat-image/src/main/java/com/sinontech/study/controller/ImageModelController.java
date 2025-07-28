package com.sinontech.study.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

/**
 * <a href="https://docs.langchain4j.dev/tutorials/chat-and-language-models/#multimodality">LangChain4j 多模态教程</a>
 */
@RestController
@Slf4j
public class ImageModelController
{
    @Autowired
    private ChatModel chatModel;
    @Value("classpath:static/images/mi.jpg")
    private Resource resource;

    @Value("classpath:static/images/执照.png")
    private Resource resource_zhizhao;

    /**
     * 通过Base64编码将图片转化为字符串
     * 结合ImageContent和TextContent形成UserMessage一起发送到模型进行处理。
     */
    @GetMapping(value = "/image/call")
    public String readImageContent() {
        return aihanderService(resource, "从下面图片种获取来源网站名称，股价走势和5月30号股价");
    }

    @GetMapping(value = "/image/callzhizhao")
    public String readImageContent_zhizhao() {
        return aihanderService(resource_zhizhao, "从下面图片种获取公司名称，信用代码、经营范围、法定代表人、住所等信息");
    }

    private String aihanderService(Resource imageResource, String prompt) {
        try {
            if (!imageResource.exists()) {
                log.error("图片文件不存在: {}", imageResource.getDescription());
                return "图片文件不存在";
            }
            // 第一步，图片转码：通过Base64编码将图片转化为字符串
            byte[] byteArray = imageResource.getContentAsByteArray();
            String base64Data = Base64.getEncoder().encodeToString(byteArray);
            String contentType = imageResource.getFilename().endsWith(".png") ? "image/png" : "image/jpg";

            // 第二步，提示词指定：结合ImageContent和TextContent一起发送到模型进行处理。
            UserMessage userMessage = UserMessage.from(
                    TextContent.from(prompt),
                    ImageContent.from(base64Data, contentType)
            );
            // 第三步，API调用：使用OpenAiChatModel来构建请求，并通过chat()方法调用模型。
            // 请求内容包括文本提示和图片，模型会根据输入返回分析结果。
            ChatResponse chatResponse = chatModel.chat(userMessage);

            // 第四步，解析与输出：从ChatResponse中获取AI大模型的回复，打印出处理后的结果。
            String result = chatResponse.aiMessage().text();
            // 后台打印
            log.info(result);
            // 返回前台
            return result;
        } catch (IOException e) {
            log.error("处理图片时发生错误", e);
            return "处理图片时发生错误";
        }
    }
}
