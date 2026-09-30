package com.example.budgetting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
public class GeminiTranscriptionIT {

    @Autowired
    private ChatModel chatModel;

    @Test
    void should_transcribeAudio_when_provided() {
        // Carrega o arquivo de áudio localizado em src/test/resources/audio/recording-1.ogg
        var audioResource = new ClassPathResource("audio/recording-1.ogg");
        var chatClient = ChatClient.builder(chatModel).build();

        String transcript = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("Transcreva com precisão o áudio enviado. Retorne apenas o texto transcrito.")
                        .media(MimeTypeUtils.parseMimeType("audio/ogg"), audioResource)
                )
                .call()
                .content();

        System.out.println("=== Resultado da Transcrição (Gemini Test) ===");
        System.out.println(transcript);

        // Valida se o Gemini retornou algum texto
        assertThat(transcript).isNotBlank();
    }
}
