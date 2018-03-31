package com.vsocolov.vendingmachine.productstore;

import com.vsocolov.vendingmachine.productstore.data.Product;

public interface ProductStore {

    Product getProduct(int productId);

    Product saveProduct(Product product);
}
