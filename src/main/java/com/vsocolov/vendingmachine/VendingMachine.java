package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.coinsstorage.CoinsStorage;
import com.vsocolov.vendingmachine.coinsstorage.impl.CoinsStorageImpl;
import com.vsocolov.vendingmachine.data.Change;
import com.vsocolov.vendingmachine.data.Product;
import com.vsocolov.vendingmachine.enums.Coin;
import com.vsocolov.vendingmachine.financial.FinancialOperations;
import com.vsocolov.vendingmachine.financial.impl.FinancialOperationsImpl;
import com.vsocolov.vendingmachine.productstorage.ProductStorage;
import com.vsocolov.vendingmachine.productstorage.impl.ArrayProductStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vsocolov.vendingmachine.helpers.AssertionHelper.*;

public final class VendingMachine implements VendingMachineAdministration, VendingMachineConsumer {

    private final ProductStorage productStorage;

    private final CoinsStorage coinsStorage;

    private final FinancialOperations financialOperations;

    private final List<Coin> coins;

    private final Object mutex = new Object();

    public VendingMachine(final int capacity, final List<Coin> coins) {
        this.coins = coins;
        productStorage = new ArrayProductStorage(capacity);
        coinsStorage = new CoinsStorageImpl(coins);
        financialOperations = new FinancialOperationsImpl();
    }

    @Override
    public int getQuantity(final int slot) {
        return productStorage.getProduct(slot).getQuantity();
    }

    @Override
    public void setQuantity(final int slot, final int quantity) {
        assertValueIsPositive(quantity);
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
        assertValueIsPositive(price);
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
        assertValueIsPositive(amount);
        coinsStorage.setCoinAmount(coin, amount);
    }

    @Override
    public List<Coin> buyProduct(final int slot, final List<Coin> coinsToPay) {
        final List<Coin> coinsToPayCopy = new ArrayList<>(coinsToPay);
        int paidAmount = coinsToPayCopy.stream().mapToInt(Coin::getAmount).sum();

        synchronized (mutex) {
            final Product product = productStorage.getProduct(slot);

            assertProductAvailable(product.getQuantity());
            assertEnoughMoney(product.getPrice(), paidAmount);

            final List<List<Change>> changes = financialOperations.calculateChanges(paidAmount - product.getPrice(), coins);
            final List<Change> availableChange = selectAvailableChange(changes);

            assertEnoughChange(availableChange, paidAmount, product.getPrice());

            // increase coins amount in storage by adding paid money
            coinsToPayCopy.forEach(coin -> coinsStorage.increaseCoinAmount(coin, 1));

            // decrease product quantity
            productStorage.saveProduct(new Product(product.getId(), product.getName(), product.getPrice(),
                    product.getQuantity() - 1));

            // decrease coins amount in storage by extracting change amount
            availableChange.forEach(change -> coinsStorage.decreaseCoinAmount(change.getCoin(), change.getQuantity()));

            //return change list
            return changeToCoins(availableChange);
        }
    }

    private List<Coin> changeToCoins(final List<Change> changeList) {
        return changeList.stream()
                .map(change -> IntStream.rangeClosed(1, change.getQuantity()).mapToObj(index -> change.getCoin()))
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    private List<Change> selectAvailableChange(final List<List<Change>> changes) {
        return changes.stream()
                .filter(this::isAvailableChange)
                .findFirst()
                .orElse(Collections.emptyList());
    }

    private boolean isAvailableChange(final List<Change> changeList) {
        return changeList.stream().noneMatch(change ->
                coinsStorage.getCoinAmount(change.getCoin()) < change.getQuantity());
    }
}
