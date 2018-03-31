package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import com.vsocolov.vendingmachine.productstorage.data.Product;
import com.vsocolov.vendingmachine.productstorage.impl.ArrayProductStorage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public final class VendingMachine implements VendingMachineAdministration, VendingMachineConsumer {

    private final List<Coin> coins;
    private final ProductStorage productStorage;

    public VendingMachine(final int capacity, final List<Coin> coins) {
        this.coins = new ArrayList<>(coins);
        this.productStorage = new ArrayProductStorage(capacity);
    }

    @Override
    public int getQuantity(final int slot) {
        return productStorage.getProduct(slot).getQuantity();
    }

    @Override
    public void setQuantity(final int slot, final int quantity) {
        synchronized (productStorage) {
            final Product product = productStorage.getProduct(slot);
            final Product forUpdate = new Product(slot, product.getName(), product.getPrice(), quantity);
            productStorage.saveProduct(forUpdate);
        }
    }

    @Override
    public int getPrice(final int slot) {
        return productStorage.getProduct(slot).getPrice();
    }

    @Override
    public void setPrice(final int slot, final int price) {
        synchronized (productStorage) {
            final Product product = productStorage.getProduct(slot);
            final Product forUpdate = new Product(slot, product.getName(), price, product.getQuantity());
            productStorage.saveProduct(forUpdate);
        }
    }

    @Override
    public String getName(final int slot) {
        return productStorage.getProduct(slot).getName();
    }

    @Override
    public void setName(final int slot, final String name) {
        synchronized (productStorage) {
            final Product product = productStorage.getProduct(slot);
            final Product forUpdate = new Product(slot, name, product.getPrice(), product.getQuantity());
            productStorage.saveProduct(forUpdate);
        }
    }

    @Override
    public int getCoinAmount(final Coin coin) {
        throw new NotImplementedException();
    }

    @Override
    public void setCoinAmount(final Coin coin, final int amount) {
        throw new NotImplementedException();
    }
}
