package br.com.gustavoedev.main.modules.product.entities;

import java.util.List;

public class OrderRequest {

    private List<ItemRequest> items;

    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }
}