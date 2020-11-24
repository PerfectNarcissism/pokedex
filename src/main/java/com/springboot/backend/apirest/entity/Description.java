package com.springboot.backend.apirest.entity;

public class Description {
	
	String description;
	Child language;
	
	public Description() {
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Child getLanguage() {
		return language;
	}
	public void setLanguage(Child language) {
		this.language = language;
	}

}
