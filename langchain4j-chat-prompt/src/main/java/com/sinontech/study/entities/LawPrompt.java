package com.sinontech.study.entities;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;


@Data
@StructuredPrompt("根据JAVA{{legal}}，解答以下问题：{{question}},输出格式为{{format}}")
public class LawPrompt
{
    private String legal;
    private String question;
    private String format;
}
