package com.sinontech.study.config.llmfactory;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "model")
public class ModelProperties {
    private Map<String, ModelConfig> models = new HashMap<>();

    @Data
    public static class ModelConfig {
        private String type;
        private String apiKey;
        private String modelName;
        private String baseUrl;
    }
}
