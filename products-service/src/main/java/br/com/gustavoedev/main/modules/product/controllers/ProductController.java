package br.com.gustavoedev.main.modules.product.controllers;

import br.com.gustavoedev.main.exceptions.ProductFoundException;
import br.com.gustavoedev.main.modules.product.ProductRepository;
import br.com.gustavoedev.main.modules.product.entities.ItemRequest;
import br.com.gustavoedev.main.modules.product.entities.OrderRequest;
import br.com.gustavoedev.main.modules.product.entities.ProductEntity;
import br.com.gustavoedev.main.modules.product.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PostMapping("/existence-check")
    public ResponseEntity<Object> checkProductsExistence(@RequestBody OrderRequest request) {
        List<UUID> requestedIds = request.getItems().stream()
                .map(ItemRequest::getProductId)
                .collect(Collectors.toList());

        if (requestedIds.isEmpty()) {
            return ResponseEntity.badRequest().body("A lista de itens não pode estar vazia.");
        }

        List<ProductEntity> foundProducts = productService.findProductsByIds(requestedIds);

        if (foundProducts.size() != requestedIds.size()) {
            List<UUID> foundIds = foundProducts.stream()
                    .map(ProductEntity::getId)
                    .collect(Collectors.toList());

            List<UUID> notFoundIds = new ArrayList<>(requestedIds);
            notFoundIds.removeAll(foundIds);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produtos não encontrados com os IDs: " + notFoundIds);
        }

        return ResponseEntity.ok(foundProducts);
    }

}
