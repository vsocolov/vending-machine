package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.enums.Coin;

import java.util.List;

public interface VendingMachineConsumer {
    List<Coin> buyProduct(int slot, List<Coin> coins);
}
