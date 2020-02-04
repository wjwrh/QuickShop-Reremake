package org.maxgamer.quickshop.shop.shopstack;

import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.economy.Economy;
import org.maxgamer.quickshop.shop.Shop;

public class EconomyShopStack implements ShopStack {
  Economy economy;
  double moneyPerStack;
  boolean allowLoan;
  public EconomyShopStack (@NotNull Economy economy,double moneyPerStack, boolean allowLoan){
    this.economy = economy;
    this.moneyPerStack = moneyPerStack;
    this.allowLoan = allowLoan;
  }
  @Override
  public TransactionResult buy(@NotNull Player p, @NotNull Shop shop, int amount) {
    double total = moneyPerStack * amount;
    if(!allowLoan){
      double hasMoney = economy.getBalance(p.getUniqueId());
      if(hasMoney < total){
        return TransactionResult.FAILED_CANTAFFORD;
      }
    }
    boolean response = economy.transfer(p.getUniqueId(),shop.getOwner(),amount*moneyPerStack);
    if(!response){
      return TransactionResult.INTERNAL_ERROR;
    }
    return TransactionResult.SUCCESS;

  }

  @Override
  public TransactionResult sell(@NotNull Player p, @NotNull Shop shop, int amount) {
    double total = moneyPerStack * amount;
    if(!allowLoan){
      double hasMoney = economy.getBalance(shop.getOwner());
      if(hasMoney < total){
        return TransactionResult.FAILED_OWNER_CANTAFFORD;
      }
    }
    boolean response = economy.transfer(shop.getOwner(),p.getUniqueId(),amount*moneyPerStack);
    if(!response){
      return TransactionResult.INTERNAL_ERROR;
    }
    return TransactionResult.SUCCESS;
  }

  @Override
  public TransactionResult add(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(player!=null,"OfflinePlayer cannot be null in EconomyShopStack");
    economy.deposit(player.getUniqueId(),amount*moneyPerStack);
    return null;
  }

  @Override
  public TransactionResult remove(int amount, @Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(player!=null,"OfflinePlayer cannot be null in EconomyShopStack");
    economy.withdraw(player.getUniqueId(),amount*moneyPerStack);
    return null;
  }
}
