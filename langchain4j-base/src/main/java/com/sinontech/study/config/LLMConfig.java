package com.sinontech.study.config;

import com.sinontech.study.config.redis.RedisChatMemoryStore;
import com.sinontech.study.entity.ChatHistory;
import com.sinontech.study.service.lc4j.RegularChatAssistant;
import com.sinontech.study.service.lc4j.StreamingChatAssistant;
import com.sinontech.study.tool.RingOrderTool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import jakarta.annotation.Resource;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import com.sinontech.study.config.llmfactory.ModelFactory;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LLMConfig
{
    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private ModelFactory modelFactory;

    @Resource
    private RingOrderTool ringOrderTool;

    /**
     * 创建聊天模型实例
     * @return ChatModel实例，使用"qwen"模型配置
     */
    @Bean
    public ChatModel chatModel()
    {
        return modelFactory.createChatModel("qwen");
    }

    /**
     * 创建带内存的常规聊天助手
     * @param chatModel 聊天模型实例
     * @return 配置了Redis内存存储的RegularChatAssistant实例
     */
    @Bean(name = "regularChatAssistant")
    public RegularChatAssistant chatMemoryAssistant(ChatModel chatModel,EmbeddingStore<TextSegment> embeddingStore)
    {

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)
                .chatMemoryStore(redisChatMemoryStore)
                .build();

        return AiServices.builder(RegularChatAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .tools(ringOrderTool)
                .build();
    }

    /**
     * 创建流式聊天模型实例
     * @return StreamingChatModel实例，使用"qwen"模型配置
     */
    @Bean
    public StreamingChatModel streamingChatModel(){
        return modelFactory.createStreamingChatModel("qwen");
    }

    /**
     * 创建带内存的流式聊天助手
     * @param streamingChatModel 流式聊天模型实例
     * @return 配置了Redis内存存储的StreamingChatAssistant实例
     */
    @Bean(name = "streamingChatAssistant")
    public StreamingChatAssistant chatAssistant(StreamingChatModel streamingChatModel){
        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)
                .chatMemoryStore(redisChatMemoryStore)
                .build();

        return AiServices.builder(StreamingChatAssistant.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(ringOrderTool)
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel()
    {
        return OpenAiEmbeddingModel.builder()
                .apiKey(System.getenv("aliQwen-api"))
                .modelName("text-embedding-v3-large")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }
    /**
     * 创建Qdrant客户端
     * @return
     */
    @Bean
    public QdrantClient qdrantClient() {
        /**
         * 创建并配置Qdrant客户端实例，建立与Qdrant向量数据库的连接
         * 仅负责建立连接通道，不执行任何集合创建操作
         */
        QdrantGrpcClient.Builder grpcClientBuilder =
                QdrantGrpcClient.newBuilder("192.168.100.100", 6334, false);
        return new QdrantClient(grpcClientBuilder.build());
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        /**
         * 这是连接到已有集合的配置，而非创建新集合
         * 若指定的集合不存在，后续操作会抛出"集合不存在"的异常
         */
        return QdrantEmbeddingStore.builder()
                .client(qdrantClient())
                .collectionName("test-qdrant")
                .build();
    }



    /**
     * 内存嵌入存储实例
     * @return InMemoryEmbeddingStore实例
     */
    @Bean
    public InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }



}
