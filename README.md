🚀 Spring AI + Google Gemini: Transcrição de Áudio e Chat

Este projeto é uma demonstração completa da integração do Spring AI com o Google Gemini AI. Ele engloba funcionalidades de geração de texto, chamadas de funções (Tool Calling), modelos fluentes de chat e a transcrição de arquivos de áudio utilizando recursos multimodais do Gemini.

🛠️ Tecnologias Utilizadas

Java 17+ ou Java 21

Spring Boot 3.x

Spring AI (Google GenAI / Gemini Starter)

Maven / Gradle

JUnit 5 (para os testes unitários e de integração)

📋 Pré-requisitos

Obter a API Key do Google Gemini:
Acesse o Google AI Studio para gerar uma chave de API (GEMINI_API_KEY).

Configuração da Variável de Ambiente:
Configure a variável no seu sistema operacional ou diretamente no IntelliJ IDEA:

export GEMINI_API_KEY="sua_api_key_aqui"


⚙️ Configuração do Projeto (application.yml)

Adicione as configurações básicas da API do Gemini no arquivo src/main/resources/application.yml (ou application.properties):

spring:
application:
name: spring-ai-gemini-demo
ai:
google:
genai:
api-key: ${GEMINI_API_KEY}
chat:
options:
model: gemini-3.8-flash 
temperature: 0.7


📂 Estrutura das Classes Principais

🎮 Controllers (src/main/java/.../controller)

ChatClientController:
Utiliza a interface fluente ChatClient do Spring AI para gerenciar interações flexíveis, prompts com contexto e respostas formatadas.

ChatModelController:
Interface direta de baixo nível com o ChatModel do Gemini para envio direto de mensagens e geração de respostas brutas.

TranscriptionController:
Controller responsável por receber arquivos de áudio via requisição HTTP (multipart/form-data) e enviar ao Gemini para transcrição e análise de conteúdo multimodal.

🧪 Estrutura das Classes de Teste (src/test/java/...)

A suíte de testes demonstra interações específicas e validações de comportamento do Gemini:

GeminiChatClientTest:
Testa a construção e respostas usando a API de alto nível ChatClient.

GeminiChatModelTest:
Valida chamadas diretas ao ChatModel e parâmetros customizados (temperatura, tokens de saída, etc).

GeminiTranscriptionTest:
Garante que o carregamento local de arquivos de áudio (.wav, .mp3) envie os dados corretamente ao Gemini e retorne a transcrição textual esperada.

ToolCallingTest:
Demonstra o uso da funcionalidade de Function Calling / Tool Calling do Gemini, permitindo que a IA invoque métodos Java externos dinamicamente para resolver tarefas específicas.

🚀 Como Executar

Executando a Aplicação Principal

No terminal do IntelliJ ou no prompt de comando:

# Com Maven
./mvnw spring-boot:run

# Com Gradle
./gradlew bootRun


A aplicação subirá na porta padrão 8080.

Executando os Testes

Para rodar a suíte de testes do Gemini:

# Executar todos os testes
./mvnw test

# Executar um teste específico
./mvnw test -Dtest=GeminiTranscriptionTest


📡 Exemplos de Endpoints

1. Transcrição de Áudio

Envia um arquivo de áudio para ser transcrito pelo Gemini.

Endpoint: POST /api/transcription

Content-Type: multipart/form-data

Body: file (arquivo de áudio .mp3 ou .wav)

curl -X POST http://localhost:8080/api/transcription \
-F "file=@/caminho/para/seu/audio.mp3"


2. Conversação via ChatClient

Endpoint: GET /api/chat-client?message=Olá, explique o Spring AI

curl "http://localhost:8080/api/chat-client?message=Ola"


3. Conversação via ChatModel

Endpoint: GET /api/chat-model?message=Quais as vantagens do Gemini?

curl "http://localhost:8080/api/chat-model?message=Teste"
