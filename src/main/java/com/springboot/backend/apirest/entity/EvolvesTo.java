package com.springboot.backend.apirest.entity;

import java.util.List;

public class EvolvesTo {
	
	Child species;
	List<EvolvesTo> evolves_to;
	
	public EvolvesTo() {
	}

	public Child getSpecies() {
		return species;
	}

	public void setSpecies(Child species) {
		this.species = species;
	}

	public List<EvolvesTo> getEvolves_to() {
		return evolves_to;
	}

	public void setEvolves_to(List<EvolvesTo> evolves_to) {
		this.evolves_to = evolves_to;
	}
}
