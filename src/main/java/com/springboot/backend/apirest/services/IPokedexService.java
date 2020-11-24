package com.springboot.backend.apirest.services;

import java.util.List;

import com.springboot.backend.apirest.entity.Characteristics;
import com.springboot.backend.apirest.entity.EvolutionChain;
import com.springboot.backend.apirest.entity.Pokemon;

public interface IPokedexService {
	
	public Pokemon getPokemon(String name);
	
	public Characteristics getCharacteristics(int id);
	
	public EvolutionChain getEvolutions(String name);
	
	public List<String> getUrlImages(List<String> urls);

}
