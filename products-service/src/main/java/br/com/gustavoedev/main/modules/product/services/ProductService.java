package br.com.gustavoedev.main.modules.product.services;

import br.com.gustavoedev.main.exceptions.ProductFoundException;
import br.com.gustavoedev.main.modules.product.ProductRepository;
import br.com.gustavoedev.main.modules.product.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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

    public List<ProductEntity> getAllProducts() {
        return this.productRepository.findAll();
    }

    public ProductEntity findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
    }

}
