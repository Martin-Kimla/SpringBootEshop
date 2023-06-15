package spring.eshop.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import spring.eshop.data.entities.ProductEntity;
import spring.eshop.models.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(ProductDTO source);
    ProductDTO toDTO(ProductEntity source);

    void updateProductDTO(ProductDTO source, @MappingTarget ProductDTO target);

    void updateProductEntity(ProductDTO source, @MappingTarget ProductEntity target);


}
