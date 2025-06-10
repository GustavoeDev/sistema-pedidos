package br.com.gustavoedev.main.modules.product.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O campo [name] é obrigatório")
    private String name;

    @NotNull(message = "O campo [quantity] é obrigatório")
    @Positive(message = "O campo [quantity] só recebe valores inteiros positivos")
    private Integer quantity;

    @NotNull(message = "O campo [price] é obrigatório")
    private Double price;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
