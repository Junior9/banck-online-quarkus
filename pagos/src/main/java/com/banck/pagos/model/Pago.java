package com.banck.pagos.model;

public class Pago {
	
	private String idUser;
	private String idProducto;
	private String idTransacion;
	
	public Pago() {}

	public Pago(String idUser, String idProducto, String idTransacion, Integer total) {
		super();
		this.idUser = idUser;
		this.idProducto = idProducto;
		this.idTransacion = idTransacion;
		this.total = total;
	}
	
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getIdTransacion() {
		return idTransacion;
	}
	public void setIdTransacion(String idTransacion) {
		this.idTransacion = idTransacion;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	private Integer total;
	

}
