package com.quarkus.model;

public class TransacionBank {
    String idUser;
    String amount;
    String type;
    
    public TransacionBank(){}
    
    public TransacionBank(String idUser,String amount,String type){
    	this.amount = amount;
    	this.idUser = idUser;
    	this.type = type;
    }
    
    public String getAmount(){
        return this.amount;
    }
    
    public String getIdUser() {
    	return this.idUser;
    }
    
    public String getType() {
    	return this.type;
    }
}
