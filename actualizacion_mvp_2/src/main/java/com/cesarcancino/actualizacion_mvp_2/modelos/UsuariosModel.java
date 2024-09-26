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
@Table(name="usuarios")
@Data 
public class UsuariosModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String correo;
	private String password;
	
	private String token;
	
	private Date fecha;
	
	@ManyToOne()
	@JoinColumn(name="perfil_id")
	private PerfilModel perfilId;
	
	@ManyToOne()
	@JoinColumn(name="estados_id")
	private EstadosModel estadosId;
	
	public UsuariosModel(String nombre, String correo, String password, String token, Date fecha, PerfilModel perfilId, EstadosModel estadosId) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.token = token;
		this.fecha = fecha;
		this.perfilId = perfilId;
		this.estadosId = estadosId;
	}
	public UsuariosModel() {
		super();
	}
}









