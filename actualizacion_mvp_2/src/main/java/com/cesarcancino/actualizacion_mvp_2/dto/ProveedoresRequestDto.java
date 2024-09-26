package com.cesarcancino.actualizacion_mvp_2.dto;

import lombok.Data;

//@Data
public class ProveedoresRequestDto {
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
