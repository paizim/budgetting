    package com.example.budgetting;

    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
    import org.springframework.ai.chat.client.ChatClient;
    import org.springframework.ai.chat.model.ChatModel;
    import org.springframework.ai.tool.annotation.Tool;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;

    import static org.assertj.core.api.Assertions.assertThat;

    @SpringBootTest
    @EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")

    public class TooCallingIT {
        @Autowired
        ChatModel chatModel;

        static class MathTools{
            @Tool(description = "soma dois números inteiros, a e b")
            public int add(int a, int b){
                return a+b;
            }

            @Tool(description = "subtrai dois números inteiros, a e b")
            public int diff(int a, int b){
                return a-b;
            }

        }

        @Test
        void should_executeSum_when_prompted(){
            var chatClient =  ChatClient.builder(chatModel)
                    .defaultSystem("você é um matemático")
                    .defaultTools(new MathTools())
                    .build();
            var response = chatClient.prompt("soma 10 mais 20, depois subtraia 30 do resultado anterior, exiba apenas a resposta final")
                    .call().content();

            assertThat(response).isEqualTo("0");

            System.out.println(response);
        }
    }

