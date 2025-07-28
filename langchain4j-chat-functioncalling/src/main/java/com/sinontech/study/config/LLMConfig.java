package com.sinontech.study.config;

import com.sinontech.study.service.FunctionAssistant;
import com.sinontech.study.service.FunctionAssistantHigh;
import com.sinontech.study.service.InvoiceHandler;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class LLMConfig
{
    @Bean
    public ChatModel chatModel()
    {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    /**
    * @Description: 第一组 Low Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#low-level-tool-api
    */
    @Bean
    public FunctionAssistant functionAssistant1(ChatModel chatModel)
    {
        // 工具说明 ToolSpecification
        ToolSpecification toolSpecification = ToolSpecification.builder()
                    .name("开具发票助手")
                    .description("根据用户提交的开票信息，开具发票")
                    .parameters(JsonObjectSchema.builder()
                                .addStringProperty("companyName", "公司名称")
                                .addStringProperty("dutyNumber", "税号序列")
                                .addStringProperty("amount", "开票金额，保留两位有效数字")
                            .build())
                .build();


        // 业务逻辑 ToolExecutor
        ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
            System.out.println(toolExecutionRequest.id());
            System.out.println(toolExecutionRequest.name());
            String arguments1 = toolExecutionRequest.arguments();
            /**
             * 调用真实业务逻辑方法
             */
            System.out.println("arguments1****》 " + arguments1);
            return "开具成功";
        };

        return AiServices.builder(FunctionAssistant.class)
                .chatModel(chatModel)
                .tools(Map.of(toolSpecification, toolExecutor)) // Tools (Function Calling)
                .build();
    }



    /**
    * @Description: 第二组 High Level Tool API
     * https://docs.langchain4j.dev/tutorials/tools#high-level-tool-api
    */
    @Bean
    public FunctionAssistantHigh functionAssistantTool(ChatModel chatModel)
    {
        return AiServices.builder(FunctionAssistantHigh.class)
                    .chatModel(chatModel)
                    .tools(new InvoiceHandler())
                .build();
    }
}
