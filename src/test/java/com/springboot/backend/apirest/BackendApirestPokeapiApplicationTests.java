package com.springboot.backend.apirest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.backend.apirest.entity.EvolutionChain;
import com.springboot.backend.apirest.entity.Pokemon;
import com.springboot.backend.apirest.services.PokedexServiceImpl;

@SpringBootTest
class BackendApirestPokeapiApplicationTests {

	@Autowired
	private PokedexServiceImpl pokedexService;
	
	Pokemon pokemon;
	EvolutionChain evolutionChain;
	List<String> urls;
	
	@Test
	@BeforeEach
	void contextLoads() {
		pokemon = new Pokemon();
		evolutionChain = new EvolutionChain();
		urls = new ArrayList<String>();
		urls.add("https://pokeapi.co/api/v2/pokemon-form/1/");
	}
	
	@Test
	public void testGetPokemon() {
		pokemon = pokedexService.getPokemon("bulbasaur");
		assertTrue(pokemon.getId()==1);
	}
	
	@Test
	public void testGetPokemonFail() {
		try {
			pokemon = pokedexService.getPokemon("agumon");
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertNull(pokemon.getName());
	}
	
	@Test
	public void testGetEvolutions() {
		
		evolutionChain = pokedexService.getEvolutions("venusaur");
		assertTrue(evolutionChain.getAllEvolutions().equals("bulbasaur ivysaur venusaur "));
	}
	
	@Test
	public void testGetEvolutionsFail() {
		try {
			evolutionChain = pokedexService.getEvolutions("patamon");
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertNull(evolutionChain.getChain());
	}
	
	@Test
	public void testGetUrlImages() {
		List<String> urlImages = new ArrayList<String>();
		urlImages = pokedexService.getUrlImages(urls);
		assertTrue(urlImages.get(0).equals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"));
	}
	
	@Test
	public void testGetUrlImagesFail() {
		List<String> urlImages = new ArrayList<String>();
		urls = new ArrayList<String>();
		urls.add("https://pokeapi.co/api/v2/pokemon-form/300000/");
		try {
			urlImages = pokedexService.getUrlImages(urls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertTrue(urlImages.isEmpty());
	}

}
