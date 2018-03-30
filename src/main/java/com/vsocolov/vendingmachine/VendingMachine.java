package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.api.VendingMachineAdministration;
import com.vsocolov.vendingmachine.api.VendingMachineConsumer;
import com.vsocolov.vendingmachine.enums.Coin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

public class VendingMachine implements VendingMachineAdministration, VendingMachineConsumer {

    private final int capacity;
    private final List<Coin> coins;

    public VendingMachine(final int capacity, final List<Coin> coins) {
        this.capacity = capacity;
        this.coins = Collections.synchronizedList(coins);
    }

    @Override
    public int getQuantity(int slot) {
        throw new NotImplementedException();
    }

    @Override
    public void setQuantity(int slot, int quantity) {
        throw new NotImplementedException();
    }

    @Override
    public int getPrice(int slot) {
        throw new NotImplementedException();
    }

    @Override
    public void setPrice(int slot, int price) {
        throw new NotImplementedException();
    }

    @Override
    public int getCoinAmount(Coin coin) {
        throw new NotImplementedException();
    }

    @Override
    public void setCoinAmount(Coin coin, int amount) {
        throw new NotImplementedException();
    }
}
