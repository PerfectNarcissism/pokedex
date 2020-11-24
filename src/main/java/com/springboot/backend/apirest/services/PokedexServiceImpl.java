package com.springboot.backend.apirest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.springboot.backend.apirest.entity.Characteristics;
import com.springboot.backend.apirest.entity.Evolution;
import com.springboot.backend.apirest.entity.EvolutionChain;
import com.springboot.backend.apirest.entity.Form;
import com.springboot.backend.apirest.entity.Pokemon;
import com.springboot.backend.apirest.resttemplate.RestTemplateConfig;

@Service
public class PokedexServiceImpl implements IPokedexService{
	
	private RestTemplate restTemplate;
	
	@Override
	public Pokemon getPokemon(String name) throws HttpClientErrorException{
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Pokemon pokemon = new Pokemon();
		pokemon=restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/"+name, Pokemon.class);
		return pokemon;
	}
	
	@Override
	public Characteristics getCharacteristics(int id) {
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Characteristics characteristics = new Characteristics();
		characteristics=restTemplate.getForObject("https://pokeapi.co/api/v2/characteristic/"+id, Characteristics.class);
		return characteristics;
	}
	
	@Override
	public EvolutionChain getEvolutions(String name) {
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		Evolution evolution = new Evolution();
		evolution =restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/"+name, Evolution.class);
		EvolutionChain evolChain = new EvolutionChain();
		evolChain = restTemplate.getForObject(evolution.getEvolution_chain().getUrl(), EvolutionChain.class);
		return evolChain;
	}

	@Override
	public List<String> getUrlImages(List<String> urls) {
		List<String> images = new ArrayList<String>();
		restTemplate = RestTemplateConfig.initRestTemplateForPdfAsByteArrayAndSelfSignedHttps();
		for (String url : urls) {
			Form form = restTemplate.getForObject(url, Form.class);
			images.add(form.getSprites().getFront_default());
		}
		return images;
	}

}
