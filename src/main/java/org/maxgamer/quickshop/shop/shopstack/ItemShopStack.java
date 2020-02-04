package org.maxgamer.quickshop.shop.shopstack;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.shop.Shop;
import org.maxgamer.quickshop.util.Util;

@AllArgsConstructor
public class ItemShopStack implements ShopStack {
  ItemStack item;
  int itemsPerStack;

  @Override
  public TransactionResult buy(@NotNull Player p, @NotNull Shop shop, int amount) {
    return null;
  }

  @Override
  public TransactionResult sell(@NotNull Player p, @NotNull Shop shop, int amount) {
    return null;
  }
  /**
   *
   * @param amount The amount stacks
   * @param inventory The inventory will add to
   * @throws IllegalArgumentException May throw IllegalArgumentException if inventory is null
   * @return success
   */
  @Override
  public TransactionResult add(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(inventory!=null, "For ItemShopStack type, Inventory can't be null");
    int remains = amount*itemsPerStack;
    while (remains > 0) {
      int stackSize = Math.min(remains, item.getMaxStackSize());
      item.setAmount(stackSize);
      inventory.addItem(item);
      remains -= stackSize;
    }
    return TransactionResult.SUCCESS;
  }

  /**
   *
   * @param amount The amount items
   * @param inventory The inventory will remove from
   * @throws IllegalArgumentException May throw IllegalArgumentException if inventory is null
   * @return success
   */
  @Override
  public TransactionResult remove(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(inventory!=null, "For ItemShopStack type, Inventory can't be null");
    int remains = amount*itemsPerStack;
    while (remains > 0) {
      int stackSize = Math.min(remains, item.getMaxStackSize());
      item.setAmount(stackSize);
      inventory.removeItem(item);
      remains -= stackSize;
    }
    return TransactionResult.SUCCESS;
  }

  @Override
  public int getRemaining(@Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(inventory!=null, "For ItemShopStack type, Inventory can't be null");
    return Util.divByNum(Util.countItems(inventory,this.item),this.itemsPerStack);
  }

  @Override
  public int getFreeSpace(@Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(inventory!=null, "For ItemShopStack type, Inventory can't be null");
    return Util.divByNum(Util.countSpace(inventory,this.item),this.itemsPerStack);
  }
}
