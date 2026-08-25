package com.pet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
