package spring.eshop.data.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.eshop.data.entities.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
