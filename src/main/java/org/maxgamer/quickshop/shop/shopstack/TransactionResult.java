package org.maxgamer.quickshop.shop.shopstack;

public enum TransactionResult {
  SUCCESS,
  FAILED_OUTOFSTOCK,
  FAILED_STOCKFULL,
  FAILED_CANTAFFORD,
  FAILED_OWNER_CANTAFFORD,
  FAILED_PLUGIN_CANCEL
}
