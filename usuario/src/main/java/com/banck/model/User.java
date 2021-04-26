package com.banck.model;

public class User {
	
	private Integer saldo;
	private String name;
	
	public User(){}
	
	public User(String name){
		this.name = name;
		this.saldo = 0;
	}
	
	public User(String name,Integer saldo){
		this.name = name;
		this.saldo = saldo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getSaldo() {
		return this.saldo;
	}

}