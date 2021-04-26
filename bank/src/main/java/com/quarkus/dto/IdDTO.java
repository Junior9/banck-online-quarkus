package com.quarkus.dto;

public class IdDTO {
	
	String id;
	
	public IdDTO(){}
	public IdDTO(String id){
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

}
