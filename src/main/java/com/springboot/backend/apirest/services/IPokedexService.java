package com.springboot.backend.apirest.services;

import com.springboot.backend.apirest.entity.Pokemon;

public interface IPokedexService {
	
	public Pokemon getPokemon(String name);

}
