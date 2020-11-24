package com.springboot.backend.apirest.entity;

import java.util.List;

public class EvolutionChain {
	
	Chain chain;
	
	public EvolutionChain() {

	}

	public Chain getChain() {
		return chain;
	}

	public void setChain(Chain chain) {
		this.chain = chain;
	}
	
	public String getAllEvolutions() {
		String allEvol = "";
		allEvol += this.chain.getSpecies().getName()+" ";
		allEvol+=nextEvolution(this.chain.getEvolves_to());
		return allEvol;
	}
	
	private String nextEvolution(List<EvolvesTo> nextEvol) {
		String strEvol="";
		for (EvolvesTo evol : nextEvol) {
			strEvol+=evol.getSpecies().getName()+" ";
			if(evol.getEvolves_to()!= null || evol.getEvolves_to().isEmpty()) {
				strEvol+=nextEvolution(evol.getEvolves_to());
			}
		}
		return strEvol;
	}

}
