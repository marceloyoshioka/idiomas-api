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

@RestController
public class GeminiController {

	
	private final GeminiService service;
	
	public GeminiController(GeminiService service) {
		this.service = service;
	}
	
	@GetMapping("/api/traduzir")
	public String traduzir(@RequestParam String palavra) {
		return  service.traduzirParaIngles(palavra);
	}
	
	@PostMapping("/api/gera-cartoes")
	public ResponseEntity<RespostaCartoesDTO> geraCartoes(@RequestBody RequisicaoCartaoDTO dto) {
		return  ResponseEntity.ok(service.geraCartoes(dto.palavra()));
	}
	
	
}
