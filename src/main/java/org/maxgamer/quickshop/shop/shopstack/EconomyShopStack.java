package org.maxgamer.quickshop.shop.shopstack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.economy.Economy;
import org.maxgamer.quickshop.shop.Shop;
import org.maxgamer.quickshop.util.Util;
@Getter
@AllArgsConstructor
public class EconomyShopStack implements ShopStack {
  private Economy economy;
  private double moneyPerStack;
  private boolean allowLoan;
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

  @Override
  public int getRemaining(@Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    Validate.isTrue(player!=null,"OfflinePlayer cannot be null in EconomyShopStack");
    double hasMoney = economy.getBalance(player.getUniqueId());
    return Util.divByNum(hasMoney,this.moneyPerStack);
  }

  @Override
  public int getFreeSpace(@Nullable Inventory inventory, @Nullable OfflinePlayer player) {
    return Integer.MAX_VALUE; //FIXME:Player wallet is unlimited, if found some plugin limit player money, this method need rewrite
  }

  @Override
  public boolean matches(@NotNull Object obj) {
    if(obj instanceof Double){
      return (double)obj ==  this.moneyPerStack;
    }
    if(obj instanceof Integer){
      return (int)obj ==  this.moneyPerStack;
    }
    if(obj instanceof Float){
      return (float)obj ==  this.moneyPerStack;
    }
    if(obj instanceof EconomyShopStack){
      return ((EconomyShopStack) obj).getMoneyPerStack() == this.moneyPerStack;
    }
    return false;
  }
}
