package org.maxgamer.quickshop.util.update;

import org.jetbrains.annotations.Nullable;

public interface UpdaterWrapper {
  boolean executeCheck();
  @Nullable VersionScheme getVersion();
  @Nullable int getDownloads();
  @Nullable double getRating();
  @Nullable long getReleaseDate();
}
