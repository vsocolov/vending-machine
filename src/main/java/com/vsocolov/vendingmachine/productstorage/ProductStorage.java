package com.vsocolov.vendingmachine.productstorage;

import com.vsocolov.vendingmachine.data.Product;

public interface ProductStorage {

    Product getProduct(int productId);

    Product saveProduct(Product product);
}
