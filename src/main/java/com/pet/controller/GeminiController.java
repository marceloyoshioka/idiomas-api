package com.pet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pet.dto.RequisicaoCartaoDTO;
import com.pet.dto.RespostaCartoesDTO;
import com.pet.service.GeminiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class GeminiController {

	
	private final GeminiService service;
	
	public GeminiController(GeminiService service) {
		this.service = service;
	}
	
	@GetMapping("/api/traduzir")
	@Operation(
	        summary = "Traduz uma palavra do Português para o Inglês",
	        description = "Recebe uma palavra em português via Query Param e retorna a tradução simples em texto plano."
	    )
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Tradução realizada com sucesso"),
	        @ApiResponse(responseCode = "400", description = "Parâmetro de busca inválido ou ausente"),
	        @ApiResponse(responseCode = "500", description = "Erro interno ao se comunicar com o Gemini")
	    })
	public String traduzir(@RequestParam String palavra) {
		return  service.traduzirParaIngles(palavra);
	}
	
	@PostMapping("/api/gera-cartoes")
	@Operation(
	        summary = "Gera 3 cartões de estudo com base em uma palavra",
	        description = "Recebe um JSON com uma palavra em inglês no corpo da requisição e retorna 3 frases de exemplo acompanhadas de suas traduções em português."
	    )
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Cartões gerados com sucesso"),
	        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
	        @ApiResponse(responseCode = "500", description = "Erro interno ao se comunicar com o Gemini")
    })
	public ResponseEntity<RespostaCartoesDTO> geraCartoes(@RequestBody RequisicaoCartaoDTO dto) {
		return  ResponseEntity.ok(service.geraCartoes(dto.palavra()));
	}
	
	
}
