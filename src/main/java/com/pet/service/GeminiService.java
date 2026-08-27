package com.pet.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.pet.dto.RespostaCartoesDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

	private final Client client;
	private final ObjectMapper objectMapper;
    
	// Inicializa o cliente oficial do Google com a sua chave
    public GeminiService(@Value("${gemini.api.key}") String apiKey, ObjectMapper objectMapper) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.objectMapper = objectMapper;
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
    
    public RespostaCartoesDTO geraCartoes(String palavra) {
    	String prompt = """
    		    Crie 3 frases de exemplo utilizando a palavra em inglês "%s". 
    		    Para cada frase, forneça também a tradução em português.
    		    Retorne estritamente um JSON no seguinte formato:
    		    {
    		      "cartoes": [
    		        {
    		          "fraseIngles": "string",
    		          "frasePortugues": "string"
    		        }
    		      ]
    		    }
    		    """.formatted(palavra);
    	try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3.6-flash", 
                    prompt, 
                    null
            );
            
            String jsonTexto = response.text().trim();
            
            // Limpa eventuais marcadores ```json do Markdown
            jsonTexto = jsonTexto.replaceAll("^```json\\s*", "")
                                 .replaceAll("^```\\s*", "")
                                 .replaceAll("\\s*```$", "");
            
            // Converte a String JSON para o DTO de resposta
            return objectMapper.readValue(jsonTexto, RespostaCartoesDTO.class);
            
            
        } catch (Exception e) {
            // Trata o erro ou lança uma exceção customizada de runtime
            throw new RuntimeException("Erro ao se comunicar com a API do Gemini: " + e.getMessage(), e);
        }
    }
    
}


