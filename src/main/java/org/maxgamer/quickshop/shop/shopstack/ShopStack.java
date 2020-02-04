package org.maxgamer.quickshop.shop.shopstack;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.shop.Shop;

/**
 * A extendable ShopStack system.
 * It can be permissions, points, items, etc.
 */
public interface ShopStack {
  //Shop buy, shop sell etc.
    TransactionResult buy(@NotNull Player p, @NotNull Shop shop, int amount);
    TransactionResult sell(@NotNull Player p, @NotNull Shop shop, int amount);
    TransactionResult add(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player);
    TransactionResult remove(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player);
}
