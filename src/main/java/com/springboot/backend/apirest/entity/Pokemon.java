package com.springboot.backend.apirest.entity;

import java.util.List;

public class Pokemon {

	int id;
	List<Ability> abilities;
	List<Type> types;
	String weight;
	String name;
	Evolution evolution;
	
	public Pokemon() {
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<Ability> abilities) {
		this.abilities = abilities;
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Evolution getEvolution() {
		return evolution;
	}

	public void setEvolution(Evolution evolution) {
		this.evolution = evolution;
	}

	public String getAllAbilities() {
		String allAbil="";
		for (int i = 0; i < this.abilities.size(); i++) {
			Ability abil = this.abilities.get(i);
			allAbil += abil.getAbility().getName();
			if(i<this.abilities.size()-1) {
				allAbil += ", ";
			}
		}
		return allAbil;
	}
	
	public String getAllTypes() {
		String allType="";
		for (int i = 0; i < this.types.size(); i++) {
			Type typ = this.types.get(i);
			allType += typ.getType().getName();
			if(i<this.types.size()-1) {
				allType += ", ";
			}
		}
		return allType;
	}
	
}
