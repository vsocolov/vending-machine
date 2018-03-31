package com.vsocolov.vendingmachine.productstorage;

import com.vsocolov.vendingmachine.productstorage.data.Product;

public interface ProductStorage {

    Product getProduct(int productId);

    Product saveProduct(Product product);
}
