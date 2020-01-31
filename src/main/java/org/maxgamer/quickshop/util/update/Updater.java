package org.maxgamer.quickshop.util.update;

import java.io.File;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.util.Util;
import org.maxgamer.quickshop.util.update.wrappers.SpigotMCWrapper;

public class Updater {
  private QuickShop plugin;
  private File updateDatFile;
  private YamlConfiguration config;

  private String version;
  @SneakyThrows
  public Updater(QuickShop plugin){
    this.plugin = plugin;
    //noinspection ResultOfMethodCallIgnored
    new File(plugin.getDataFolder(),".update").mkdirs();
    this.updateDatFile = new File(new File(plugin.getDataFolder(),".update"),"update.dat");
    if(!this.updateDatFile.exists()){
      //noinspection ResultOfMethodCallIgnored
      this.updateDatFile.createNewFile();
    }
    try{
    this.config = YamlConfiguration.loadConfiguration(updateDatFile);
    }catch (Throwable e){
      Util.debugLog(e.getMessage()+" Cannot create updater config file, re-creating...");
      this.updateDatFile.delete();
      this.updateDatFile.createNewFile();
      this.config = YamlConfiguration.loadConfiguration(updateDatFile);
    }
  }
  public void doCheckUpdate(){
    UpdaterWrapper wrapper = new SpigotMCWrapper();
    if(!wrapper.executeCheck()){
      return;
    }
    this.version = wrapper.getVersion();
  }
  public boolean hasUpdate(){
    return this.version != null && !this.version.trim().isEmpty() &&!this.version.equals(plugin.getDescription().getVersion());
  }

  public String getVersion() {
    return this.version;
  }
}
