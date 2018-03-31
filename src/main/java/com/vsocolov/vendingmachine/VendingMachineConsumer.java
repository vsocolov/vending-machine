package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.enums.Coin;

import java.util.List;

public interface VendingMachineConsumer {
    void buyProduct(int slot, List<Coin> coins);
}
