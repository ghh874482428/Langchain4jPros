package com.sinontech.study.controller;

import com.sinontech.study.service.ChatAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Description: 知识出处，https://docs.langchain4j.dev/tutorials/response-streaming
 */
@RestController
@Slf4j
public class StreamingChatModelController
{
    @Resource //直接使用 low-level LLM API
    private StreamingChatModel streamingChatLanguageModel;
    @Resource //自己封装接口使用 high-level LLM API
    private ChatAssistant chatAssistant;


    /**
     * 需要与前端交互可以用Flux<String>
     * @param prompt
     * @return
     */
    @GetMapping(value = "/chatstream/chat")
    public Flux<String> chat(@RequestParam(value = "prompt", defaultValue = "杭州有什么好吃") String prompt)
    {
        System.out.println("---come in chat");

        return Flux.create(emitter -> {
            streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler()
            {
                @Override
                public void onPartialResponse(String partialResponse)
                {
                    log.info("partialResponse:{}",partialResponse);
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse)
                {
                    log.info("completeResponse:{}",completeResponse);
                    emitter.complete();
                }

                @Override
                public void onError(Throwable throwable)
                {
                    log.info("throwable:{}",throwable);
                    emitter.error(throwable);
                }
            });
        });
    }

    @GetMapping(value = "/chatstream/chat2")
    public void chat2(@RequestParam(value = "prompt", defaultValue = "北京有什么好吃") String prompt)
    {
        System.out.println("---come in chat2");
        streamingChatLanguageModel.chat(prompt, new StreamingChatResponseHandler()
        {
            @Override
            public void onPartialResponse(String partialResponse)
            {
                log.info("partialResponse:{}",partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse)
            {
                log.info("completeResponse:{}",completeResponse);
            }

            @Override
            public void onError(Throwable throwable)
            {
                throwable.printStackTrace();
            }
        });
    }



    @GetMapping(value = "/chatstream/chat3")
    public Flux<String> chat3(@RequestParam(value = "prompt", defaultValue = "杭州有什么好吃") String prompt)
    {
        log.info("---come in chat3");
        return chatAssistant.chatFlux(prompt);
    }
}
