package cl.tamila.modelos;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="producto")
public class ProductoMongoModel {
	@Id
	private String id;
	private String nombre;
	private String slug;
	private String descripcion;
	private Integer precio;
	private String foto;
	
	@DBRef
	private CategoriaMongoModel categoriaId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public CategoriaMongoModel getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(CategoriaMongoModel categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	
}
