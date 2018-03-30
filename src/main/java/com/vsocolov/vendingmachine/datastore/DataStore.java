package com.vsocolov.vendingmachine.datastore;

import com.vsocolov.vendingmachine.datastore.data.Product;

public interface DataStore {

    Product getProduct(int productId);

    Product saveProduct(Product product);
}
