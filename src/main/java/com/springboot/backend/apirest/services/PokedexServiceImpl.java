package com.springboot.backend.apirest.services;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.springboot.backend.apirest.entity.Pokemon;
import com.springboot.backend.apirest.resttemplate.RestTemplateConfig;

public class PokedexServiceImpl implements IPokedexService{
	
	private RestTemplate restTemplate;
	
	public Pokemon getPokemon(String name) throws HttpClientErrorException{
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Pokemon pokemon = new Pokemon();
		pokemon=restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/"+name, Pokemon.class);
		return pokemon;
	}

}
