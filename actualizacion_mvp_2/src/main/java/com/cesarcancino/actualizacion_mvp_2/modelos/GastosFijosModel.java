package com.cesarcancino.actualizacion_mvp_2.modelos;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="gastos_fijos")
@Data
public class GastosFijosModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Long monto;
	private Date fecha;
	
	@ManyToOne()
	@JoinColumn(name = "estados_id")
	private EstadosModel estadosId;
	
	@ManyToOne()
	@JoinColumn(name = "proveedores_id")
	private ProveedoresModel proveedoresId;

	public GastosFijosModel(String nombre, Long monto, Date fecha, EstadosModel estadosId,
			ProveedoresModel proveedoresId) {
		super();
		this.nombre = nombre;
		this.monto = monto;
		this.fecha = fecha;
		this.estadosId = estadosId;
		this.proveedoresId = proveedoresId;
	}

	public GastosFijosModel() {
		super();
	}
	
	
}
