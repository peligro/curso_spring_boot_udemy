package com.cesarcancino.actualizacion_mvp_2.modelos;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="gastos_por_dia")
@Data
public class GastosPorDiaModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long neto;
	private Long iva;
	private Long total;
	private Date fecha;
	@Column(length = 65535, columnDefinition = "text")
	private String descripcion;
	
	@ManyToOne()
	@JoinColumn(name = "proveedores_id")
	private ProveedoresModel proveedoresId;

	public GastosPorDiaModel(Long neto, Long iva, Long total, Date fecha, String descripcion,
			ProveedoresModel proveedoresId) {
		super();
		this.neto = neto;
		this.iva = iva;
		this.total = total;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.proveedoresId = proveedoresId;
	}

	public GastosPorDiaModel() {
		super();
	}
	
	
}

