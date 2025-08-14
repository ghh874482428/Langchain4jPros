package com.sinontech.study.controller;

import com.sinontech.study.enums.ModelType;
import com.sinontech.study.service.AiService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Description: https://docs.langchain4j.dev/tutorials/rag#rag-flavours-in-langchain4j
 */
@RestController
@Slf4j
public class RAGController
{
    @Resource
    EmbeddingStore<TextSegment> embeddingStore;
    @Resource
    AiService aiService;
    @GetMapping(value = "/rag/add")
    public String testAdd()
    {
        try {
            // 尝试使用不同的文档解析方式
            Document document = FileSystemDocumentLoader.loadDocument("E:\\ghh\\欣网卓信\\欣能汇平台\\114商圈\\114商圈业务办理开放接口协议 - 副本.doc");
            EmbeddingStoreIngestor.ingest(document,embeddingStore);
            /**
             * 1.文档分割：将Document自动分割为多个TextSegment（默认按段落分割）
             * 2.向量生成：通过Spring上下文自动获取embeddingModel生成向量
                 * 使用text-embedding-v3-large模型将文本转换为1024维向量
                 * 调用阿里云DashScope API完成实际向量化计算
             * 3.存储向量：将向量与文本片段关联后存入embeddingStore（Qdrant向量数据库）
             */
            String result = aiService.chat("456789", "来电名片的金额和资费编码有哪些？", ModelType.DASHSCOPE);
            //用户查询文本同样通过EmbeddingModel转换为向量
            //在Qdrant中执行相似度搜索获取相关文档片段
            //将检索结果与查询一起提交给LLM生成最终回答
            log.info("RAG查询结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("文档解析或RAG处理失败: {}", e.getMessage(), e);
            return "处理失败: " + e.getMessage();
        }
    }
}
