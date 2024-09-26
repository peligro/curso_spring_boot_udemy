package com.cesarcancino.actualizacion_mvp_2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UsuariosResponseDto {
	private Long id;
	private String nombre;
	private String correo;
	private String perfil;
	private Long perfil_id;
	private Long estado_id;
	private String estado;
}