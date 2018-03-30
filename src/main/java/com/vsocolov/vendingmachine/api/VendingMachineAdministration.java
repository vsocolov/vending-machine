package com.vsocolov.vendingmachine.api;

import com.vsocolov.vendingmachine.enums.Coin;

public interface VendingMachineAdministration {

    int getQuantity(int slot);

    void setQuantity(int slot, int quantity);

    int getPrice(int slot);

    void setPrice(int slot, int price);

    int getCoinAmount(Coin coin);

    void setCoinAmount(Coin coin, int amount);
}
