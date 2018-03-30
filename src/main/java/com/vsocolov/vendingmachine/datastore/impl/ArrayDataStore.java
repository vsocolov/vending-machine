package com.vsocolov.vendingmachine.datastore.impl;

import com.vsocolov.vendingmachine.datastore.DataStore;
import com.vsocolov.vendingmachine.datastore.data.Product;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;

import java.util.stream.IntStream;

import static com.vsocolov.vendingmachine.enums.ExceptionType.INVALID_PRODUCT_SLOT;

public class ArrayDataStore implements DataStore {

    private final Product[] datasource;

    private final int capacity;

    public ArrayDataStore(final int capacity) {
        this.capacity = capacity;
        datasource = fillDataSource(capacity);
    }

    /**
     * This method fill datasource with default products
     *
     * @param capacity datasource capacity
     * @return a filled array with default products
     */
    private Product[] fillDataSource(final int capacity) {
        return IntStream.range(0, capacity)
                .mapToObj(Product::new)
                .toArray(Product[]::new);
    }

    @Override
    public Product getProduct(int productId) {
        assertProductId(productId);

        synchronized (datasource) {
            return datasource[productId];
        }
    }

    @Override
    public Product saveProduct(final Product product) {
        assertProductId(product.getId());

        synchronized (datasource) {
            datasource[product.getId()] = product;
        }

        return product;
    }

    private void assertProductId(final int productId) {
        if (productId < 0 || productId >= capacity)
            throw new VendingMachineException(INVALID_PRODUCT_SLOT);
    }
}
