package com.springboot.backend.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.springboot.backend.apirest.entity.Pokemon;
import com.springboot.backend.apirest.entity.Characteristics;
import com.springboot.backend.apirest.entity.Evolution;
import com.springboot.backend.apirest.entity.EvolutionChain;
import com.springboot.backend.apirest.resttemplate.RestTemplateConfig;

@RestController
@RequestMapping("/pokedex")
public class PokedexRestController {
	
	RestTemplate restTemplate;
	private final static String LANG_EN="en";

	@GetMapping("/pokemon/{name}")
	public ResponseEntity<?> getPokemon(@PathVariable String name){
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Pokemon pokemon = new Pokemon();
			pokemon=restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/"+name, Pokemon.class);
			response.put("id", pokemon.getId());
			response.put("name", pokemon.getName());
			response.put("abilities", pokemon.getAllAbilities());
			response.put("types", pokemon.getAllTypes());
			response.put("weight", pokemon.getWeight());
		} catch (HttpClientErrorException e) {
			response.put("mensaje", "No se encontró el pokemon");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/pokemon/detail/{name}")
	public ResponseEntity<?> getPokemonDetail(@PathVariable String name){
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Map<String, Object> response = new HashMap<String, Object>();
		Pokemon pokemon = new Pokemon();
		try {
			Map<String, Object> basicInfo = new HashMap<String, Object>();
			pokemon=restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/"+name, Pokemon.class);
			basicInfo.put("abilities", pokemon.getAllAbilities());
			basicInfo.put("types", pokemon.getAllTypes());
			basicInfo.put("weight", pokemon.getWeight());
			
			response.put("info", basicInfo);
		} catch (HttpClientErrorException e) {
			response = new HashMap<String, Object>();
			response.put("mensaje", "No se encontró el pokemon");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			Characteristics characteristics = new Characteristics();
			characteristics=restTemplate.getForObject("https://pokeapi.co/api/v2/characteristic/"+pokemon.getId(), Characteristics.class);
			
			response.put("description", characteristics.getLanguageDesc(LANG_EN));
		} catch (HttpClientErrorException e) {
			response = new HashMap<String, Object>();
			response.put("mensaje", "No se encontraron características para: "+pokemon.getName());
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			Evolution evolution = new Evolution();
			evolution =restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/"+name, Evolution.class);
			
			EvolutionChain evolChain = new EvolutionChain();
			evolChain = restTemplate.getForObject(evolution.getEvolution_chain().getUrl(), EvolutionChain.class);
			
			response.put("evolutions", evolChain.getAllEvolutions());
		} catch (HttpClientErrorException e) {
			response = new HashMap<String, Object>();
			response.put("mensaje", "No se encontraron las evoluciones para: "+pokemon.getName());
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
}
