package com.springboot.backend.apirest.entity;

import java.util.List;

public class Characteristics {
	
	int id;
	List<Description> descriptions;
	
	public Characteristics() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	public String getLanguageDesc(String lang){
		String desc="";
		for (int i = 0; i < this.descriptions.size(); i++) {
			Description dDes= this.descriptions.get(i);
			if(dDes.getLanguage().getName().equals(lang)) {
				desc= dDes.getDescription();
				break;
			}
		}
		return desc;
	}

}
