package spring.eshop.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.eshop.data.entities.ProductEntity;
import spring.eshop.data.repositories.ProductRepository;
import spring.eshop.models.dto.ProductDTO;
import spring.eshop.models.dto.mappers.ProductMapper;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void create(ProductDTO product) {
        ProductEntity newProduct = productMapper.toEntity(product);
        productRepository.save(newProduct);
    }
    @Override
    public List<ProductDTO> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(i -> productMapper.toDTO(i))
                .toList();
    }
    private ProductEntity getArticleOrThrow(long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow();
    }
    @Override
    public ProductDTO getById(long productId) {
        ProductEntity fetchedProduct = getArticleOrThrow(productId);
        return productMapper.toDTO(fetchedProduct);
    }
    @Override
    public void edit(ProductDTO product) {
        ProductEntity fetchedProduct = getArticleOrThrow(product.getProductId());
        productMapper.updateProductEntity(product, fetchedProduct);
        productRepository.save(fetchedProduct);
    }
    @Override
    public void remove(long productId) {
        ProductEntity fetchedEntity = getArticleOrThrow(productId);
        productRepository.delete(fetchedEntity);
    }
}
