package com.vsocolov.vendingmachine;

import com.vsocolov.vendingmachine.enums.Coin;

public interface VendingMachineAdministration {

    int getQuantity(int slot);

    void setQuantity(int slot, int quantity);

    int getPrice(int slot);

    void setPrice(int slot, int price);

    String getName(int slot);

    void setName(int slot, String name);

    int getCoinAmount(Coin coin);

    void setCoinAmount(Coin coin, int amount);
}
