package br.com.gustavoedev.main.modules.product.entities;

// ItemRequest.java

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class ItemRequest {

    @JsonProperty("product_id")
    private UUID productId;

    private int quantity;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}