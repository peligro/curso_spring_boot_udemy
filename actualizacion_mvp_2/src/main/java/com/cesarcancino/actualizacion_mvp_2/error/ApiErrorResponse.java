package com.cesarcancino.actualizacion_mvp_2.error;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiErrorResponse {
	private int codigo;
	private String mensaje;
	
}
