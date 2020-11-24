package com.springboot.backend.apirest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.springboot.backend.apirest.entity.Pokemon;
import com.springboot.backend.apirest.entity.Characteristics;
import com.springboot.backend.apirest.entity.EvolutionChain;
import com.springboot.backend.apirest.services.PokedexServiceImpl;

@RestController
@RequestMapping("/pokedex")
public class PokedexRestController {
	
	private final static String LANG_EN="en";
	
	@Autowired
	private PokedexServiceImpl pokedexService;
	
	@GetMapping("/")
	public String index(){
		String response = "";
		response+="<h1>API POKEDEX</h1>";
		response+="<h3>Consulta Pokemon: https://pokedex-deploy.herokuapp.com/pokedex/pokemon/burmy</h3>";
		response+="<h3>Detalle Pokemon: https://pokedex-deploy.herokuapp.com/pokedex/pokemon/detail/pikachu</h3>";
		return response;
	}

	@GetMapping("/pokemon/{name}")
	public ResponseEntity<?> getPokemon(@PathVariable String name){
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Pokemon pokemon = new Pokemon();
			pokemon=pokedexService.getPokemon(name);
			response.put("id", pokemon.getId());
			response.put("name", pokemon.getName());
			response.put("abilities", pokemon.getAllAbilities());
			response.put("types", pokemon.getAllTypes());
			response.put("weight", pokemon.getWeight());
			response.put("photo", pokedexService.getUrlImages(pokemon.getFormsUrls()));
		} catch (HttpClientErrorException e) {
			response.put("mensaje", "No se encontró el pokemon");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/pokemon/detail/{name}")
	public ResponseEntity<?> getPokemonDetail(@PathVariable String name){
		Map<String, Object> response = new HashMap<String, Object>();
		Pokemon pokemon = new Pokemon();
		try {
			Map<String, Object> basicInfo = new HashMap<String, Object>();
			pokemon=pokedexService.getPokemon(name);
			basicInfo.put("abilities", pokemon.getAllAbilities());
			basicInfo.put("types", pokemon.getAllTypes());
			basicInfo.put("weight", pokemon.getWeight());
			basicInfo.put("photo", pokedexService.getUrlImages(pokemon.getFormsUrls()));
			response.put("info", basicInfo);
		} catch (HttpClientErrorException e) {
			response = new HashMap<String, Object>();
			response.put("mensaje", "No se encontró el pokemon");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			Characteristics characteristics = pokedexService.getCharacteristics(pokemon.getId());			
			response.put("description", characteristics.getLanguageDesc(LANG_EN));
		} catch (HttpClientErrorException e) {
			response = new HashMap<String, Object>();
			response.put("mensaje", "No se encontraron características para: "+pokemon.getName());
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			EvolutionChain evolChain = pokedexService.getEvolutions(pokemon.getName());			
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
