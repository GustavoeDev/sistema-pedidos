package br.com.gustavoedev.main.modules.product.controllers;

import br.com.gustavoedev.main.exceptions.ProductFoundException;
import br.com.gustavoedev.main.modules.product.ProductRepository;
import br.com.gustavoedev.main.modules.product.entities.ProductEntity;
import br.com.gustavoedev.main.modules.product.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody ProductEntity productEntity) {
        try {
            var result = productService.execute(productEntity);
            return ResponseEntity.status(201).body(result);
        } catch (ProductFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductEntity>> listAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return ResponseEntity.status(200).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable UUID productId) {
        try {
            ProductEntity result = productService.findById(productId);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
