package com.cesarcancino.actualizacion_mvp_2.dto;

import lombok.Data;

@Data
public class GastosPorDiaRequestDto {
	private Long neto;
	private Long iva;
	private Long total;
	private String descripcion;
	private Long proveedoresId;
}
