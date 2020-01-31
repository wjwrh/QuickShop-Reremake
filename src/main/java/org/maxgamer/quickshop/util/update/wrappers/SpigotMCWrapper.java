package org.maxgamer.quickshop.util.update.wrappers;

import java.io.IOException;
import java.net.URL;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.nonquickshopstuffs.com.sk89q.worldedit.util.net.HttpRequest;
import org.maxgamer.quickshop.util.update.UpdaterWrapper;

public class SpigotMCWrapper implements UpdaterWrapper {
  private String version;

  @Override
  public boolean executeCheck() {
    try {
      this.version =
          HttpRequest.get(new URL("https://api.spigotmc.org/legacy/update.php?resource=62575"))
              .execute()
              .expectResponseCode(200)
              .returnContent()
              .asString("UTF-8")
              .trim();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public @Nullable String getVersion() {
    return this.version;
  }

  @Override
  public @Nullable String getDescription() {
    return null;
  }

  @Override
  public @Nullable String getDownloadUrl() {
    return null;
  }
}
