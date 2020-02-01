package org.maxgamer.quickshop.util.update.wrappers;

import java.io.IOException;
import java.net.URL;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.nonquickshopstuffs.com.sk89q.worldedit.util.net.HttpRequest;
import org.maxgamer.quickshop.util.update.Updater;
import org.maxgamer.quickshop.util.update.UpdaterWrapper;
import org.maxgamer.quickshop.util.update.VersionScheme;

public class SpigotMCWrapper implements UpdaterWrapper {
  private VersionScheme version;

  @Override
  public boolean executeCheck() {
    try {
      this.version =
          Updater.readVersionString(
              HttpRequest.get(new URL("https://api.spigotmc.org/legacy/update.php?resource=62575"))
                  .execute()
                  .expectResponseCode(200)
                  .returnContent()
                  .asString("UTF-8")
                  .trim());
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public @Nullable VersionScheme getVersion() {
    return this.version;
  }

  @Override
  public @Nullable int getDownloads() {
    return 0;
  }

  @Override
  public @Nullable double getRating() {
    return 0;
  }

  @Override
  public @Nullable long getReleaseDate() {
    return 0;
  }
}
