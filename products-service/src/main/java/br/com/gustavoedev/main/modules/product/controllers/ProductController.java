package br.com.gustavoedev.main.modules.product.controllers;

import br.com.gustavoedev.main.exceptions.ProductFoundException;
import br.com.gustavoedev.main.modules.product.entities.ProductEntity;
import br.com.gustavoedev.main.modules.product.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
