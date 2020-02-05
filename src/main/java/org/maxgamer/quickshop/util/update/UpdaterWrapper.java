package org.maxgamer.quickshop.util.update;

import org.jetbrains.annotations.Nullable;

public interface UpdaterWrapper {
  boolean executeCheck();
  @Nullable VersionScheme getVersion();
  int getDownloads();
  double getRating();
  long getReleaseDate();
}
