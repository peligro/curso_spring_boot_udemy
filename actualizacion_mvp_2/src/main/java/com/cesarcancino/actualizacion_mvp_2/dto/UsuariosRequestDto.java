package com.cesarcancino.actualizacion_mvp_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UsuariosRequestDto {
	private String nombre;
	private String correo;
	private String password;
}
