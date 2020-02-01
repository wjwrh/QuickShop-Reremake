package org.maxgamer.quickshop.shop;

/**
 * This interface told plugin this ShopStack can be stacked.
 */
public interface Stackable {
    int getStackAmount();
    void setStackAmount(int amount);
}
