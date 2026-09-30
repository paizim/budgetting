package com.example.budgetting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transcription")
public class TranscriptionController {

    private final ChatClient chatClient;

    public TranscriptionController(ChatModel chatModel) {
        // Constrói o ChatClient utilizando o modelo do Gemini injetado
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @PostMapping
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, envie um arquivo de áudio válido.");
        }

        try {
            // Identifica o tipo do arquivo (ex: audio/ogg, audio/mp3, audio/wav)
            String contentType = file.getContentType() != null ? file.getContentType() : "audio/ogg";

            // Envia o áudio direto para o Gemini transcrever
            String transcription = chatClient.prompt()
                    .user(userSpec -> userSpec
                            .text("Transcreva com precisão todo o conteúdo do áudio enviado. Retorne apenas o texto transcrito, sem introduções ou explicações.")
                            .media(MimeTypeUtils.parseMimeType(contentType), file.getResource())
                    )
                    .call()
                    .content();

            return ResponseEntity.ok(transcription);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar a transcrição com Gemini: " + e.getMessage());
        }
    }
}