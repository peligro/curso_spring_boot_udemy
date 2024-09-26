package cl.tamila.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.tamila.modelos.CategoriaMongoModel;

public interface ICategoriaMongoRepositorio extends MongoRepository<CategoriaMongoModel, String> {
	
	public boolean existsBySlug( String slug);
	
}
