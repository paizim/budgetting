package com.example.budgetting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
public class GeminiChatModelIT {

    @Autowired
    private ChatModel chatModel;

    @Test
    void shoud_ReceiveResponse_when_chatModelIsCalled() {
        var options = GoogleGenAiChatOptions.builder()
                .model("gemini-3.8-flash")
                .build();

        var prompt = new Prompt(
                "gere um registro de budgetting, com o descrição de gasto, valor em reais e local",
                options
        );

        var response = chatModel.call(prompt).getResult().getOutput().getText();

        assertThat(response).isNotEmpty();
        System.out.println(response);
    }
}