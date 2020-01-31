package org.maxgamer.quickshop.util.update;

import org.jetbrains.annotations.Nullable;

public interface UpdaterWrapper {
  boolean executeCheck();
  @Nullable String getVersion();
  @Nullable String getDescription();
  @Nullable String getDownloadUrl();
}
