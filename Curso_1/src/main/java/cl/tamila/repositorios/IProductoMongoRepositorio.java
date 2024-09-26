package cl.tamila.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.tamila.modelos.ProductoMongoModel;

public interface IProductoMongoRepositorio extends MongoRepository<ProductoMongoModel, String>{

}
