package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.api.VendingMachineAdministration;
import com.vsocolov.vendingmachine.api.VendingMachineConsumer;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.productstore.ProductStore;
import com.vsocolov.vendingmachine.productstore.data.Product;
import com.vsocolov.vendingmachine.productstore.impl.ArrayProductStore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public final class VendingMachine implements VendingMachineAdministration, VendingMachineConsumer {

    private final int capacity;
    private final List<Coin> coins;
    private final ProductStore productStore;

    public VendingMachine(final int capacity, final List<Coin> coins) {
        this.capacity = capacity;
        this.coins = new ArrayList<>(coins);
        this.productStore = new ArrayProductStore(capacity);
    }

    @Override
    public int getQuantity(final int slot) {
        return productStore.getProduct(slot).getQuantity();
    }

    @Override
    public void setQuantity(final int slot, final int quantity) {
        synchronized (productStore) {
            final Product product = productStore.getProduct(slot);
            final Product forUpdate = new Product(slot, product.getName(), product.getPrice(), quantity);
            productStore.saveProduct(forUpdate);
        }
    }

    @Override
    public int getPrice(final int slot) {
        return productStore.getProduct(slot).getPrice();
    }

    @Override
    public void setPrice(final int slot, final int price) {
        synchronized (productStore) {
            final Product product = productStore.getProduct(slot);
            final Product forUpdate = new Product(slot, product.getName(), price, product.getQuantity());
            productStore.saveProduct(forUpdate);
        }
    }

    @Override
    public String getName(final int slot) {
        return productStore.getProduct(slot).getName();
    }

    @Override
    public void setName(final int slot, final String name) {
        synchronized (productStore) {
            final Product product = productStore.getProduct(slot);
            final Product forUpdate = new Product(slot, name, product.getPrice(), product.getQuantity());
            productStore.saveProduct(forUpdate);
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
