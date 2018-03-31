package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.coinsstorage.impl.CoinsStorageImpl;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.exceptions.VendingMachineException;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import com.vsocolov.vendingmachine.data.Product;
import com.vsocolov.vendingmachine.productstorage.impl.ArrayProductStorage;

import java.util.ArrayList;
import java.util.List;

import static com.vsocolov.vendingmachine.enums.ExceptionType.NOT_ENOUGH_MONEY;
import static com.vsocolov.vendingmachine.enums.ExceptionType.PRODUCT_NOT_AVAILABLE;

public final class VendingMachine implements VendingMachineAdministration, VendingMachineConsumer {

    private final ProductStorage productStorage;

    private final CoinsStorage coinsStorage;

    private final Object mutex = new Object();

    public VendingMachine(final int capacity, final List<Coin> coins) {
        this.productStorage = new ArrayProductStorage(capacity);
        this.coinsStorage = new CoinsStorageImpl(coins);
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
        return coinsStorage.getCoinAmount(coin);
    }

    @Override
    public void setCoinAmount(final Coin coin, final int amount) {
        coinsStorage.setCoinAmount(coin, amount);
    }

    @Override
    public void buyProduct(final int slot, final List<Coin> coins) {
        synchronized (mutex) {
            final List<Coin> coinsCopy = new ArrayList<>(coins);
            final Product product = productStorage.getProduct(slot);

            int coinsAmount = coinsCopy.stream().mapToInt(Coin::getAmount).sum();

            assertProductAvailable(product.getQuantity());
            assertEnoughMoney(product.getPrice(), coinsAmount);

            // decrease quantity
            productStorage.saveProduct(new Product(product.getId(), product.getName(),
                    product.getPrice(), product.getQuantity() - 1));
        }
    }

    private void assertProductAvailable(final int productQuantity) {
        if (productQuantity == 0)
            throw new VendingMachineException(PRODUCT_NOT_AVAILABLE);
    }

    private void assertEnoughMoney(final int productPrice, final int coinsAmount) {
        if (productPrice > coinsAmount)
            throw new VendingMachineException(NOT_ENOUGH_MONEY);
    }
}
