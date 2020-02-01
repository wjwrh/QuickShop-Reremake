package org.maxgamer.quickshop.util.update;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Builder
@Data
public class VersionScheme{
  private int major;
  private int minor;
  private int patch;
  @Nullable String desc;
}
