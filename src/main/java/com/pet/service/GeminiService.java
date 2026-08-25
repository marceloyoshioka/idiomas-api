package com.pet.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

	private final Client client;

    // Inicializa o cliente oficial do Google com a sua chave
    public GeminiService(@Value("${gemini.api.key}") String apiKey) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String traduzirParaIngles(String palavra) {
    	String prompt = "Traduza a palavra '" + palavra + "' para o inglês. Retorne APENAS a palavra traduzida, sem pontuação ou frases adicionais.";

        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3.6-flash", 
                    prompt, 
                    null
            );
            
            return response.text().trim();
        } catch (Exception e) {
            // Trata o erro ou lança uma exceção customizada de runtime
            throw new RuntimeException("Erro ao se comunicar com a API do Gemini: " + e.getMessage(), e);
        }
    }
}


/*

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;


@Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestClient restClient;

    public GeminiService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String traduzirParaIngles(String texto) {
        String prompt = "Traduza o seguinte texto para o inglês. Responda apenas com a tradução: " + texto;

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(Map.of("text", prompt)))
            )
        );

        Map<?, ?> response = restClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        return extrairTextoResposta(response);
    }

    @SuppressWarnings("unchecked")
    private String extrairTextoResposta(Map<?, ?> response) {
        try {
            List<Map<?, ?>> candidates = (List<Map<?, ?>>) response.get("candidates");
            Map<?, ?> content = (Map<?, ?>) candidates.get(0).get("content");
            List<Map<?, ?>> parts = (List<Map<?, ?>>) content.get("parts");
            return parts.get(0).get("text").toString().trim();
        } catch (Exception e) {
            return "Erro ao processar a resposta do Gemini.";
        }
    }

*/