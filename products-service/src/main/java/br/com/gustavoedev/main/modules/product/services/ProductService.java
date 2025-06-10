package br.com.gustavoedev.main.modules.product.services;

import br.com.gustavoedev.main.exceptions.ProductFoundException;
import br.com.gustavoedev.main.modules.product.ProductRepository;
import br.com.gustavoedev.main.modules.product.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity execute(ProductEntity productEntity) {
        this.productRepository.findByName(productEntity.getName())
                .ifPresent((product) -> {
                    throw new ProductFoundException();
                });

        return this.productRepository.save(productEntity);
    }

}
