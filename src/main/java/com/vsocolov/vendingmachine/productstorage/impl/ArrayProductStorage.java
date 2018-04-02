package com.vsocolov.vendingmachine.productstorage.impl;

import com.vsocolov.vendingmachine.data.Product;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;

import java.util.stream.IntStream;

import static com.vsocolov.vendingmachine.helpers.AssertionHelper.assertProductId;

public class ArrayProductStorage implements ProductStorage {

    private final Product[] dataStorage;

    private final int capacity;

    public ArrayProductStorage(final int capacity) {
        this.capacity = capacity;
        dataStorage = fillDataStorage(capacity);
    }

    @Override
    public Product getProduct(int productId) {
        assertProductId(productId, capacity);

        synchronized (dataStorage) {
            return dataStorage[productId];
        }
    }

    @Override
    public Product saveProduct(final Product product) {
        assertProductId(product.getId(), capacity);

        synchronized (dataStorage) {
            dataStorage[product.getId()] = product;
        }

        return product;
    }

    /**
     * This method fill dataStorage with default products
     *
     * @param capacity dataStorage capacity
     * @return a filled array with default products
     */
    private Product[] fillDataStorage(final int capacity) {
        return IntStream.range(0, capacity)
                .mapToObj(Product::new)
                .toArray(Product[]::new);
    }
}
