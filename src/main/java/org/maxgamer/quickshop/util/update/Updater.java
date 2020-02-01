package org.maxgamer.quickshop.util.update;

import java.io.File;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.util.Util;
import org.maxgamer.quickshop.util.update.wrappers.SpigotMCWrapper;

public class Updater {
  private QuickShop plugin;
  private File updateDatFile;
  private YamlConfiguration config;

  private VersionScheme version;
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
    if(this.version == null){
      return false;
    }
    VersionScheme pluginVer = readVersionString(plugin.getDescription().getVersion());
    if(version.getMajor() > pluginVer.getMajor()){
      return true;
    }
    if(version.getMinor() > pluginVer.getMinor()){
      return true;
    }
    return version.getPatch() > pluginVer.getPatch();
  }

  public VersionScheme getVersion() {
    return this.version;
  }

  public static VersionScheme readVersionString(@NotNull String versionStr){
    String[] explode = versionStr.split(" ");
    if(explode.length == 0){
      throw new IllegalArgumentException("The version number cannot be read: "+versionStr+" !");
    }
    String[] versionGroup = explode[0].split("\\.");
    if(versionGroup.length < 2){
      throw new IllegalArgumentException("The version number cannot be read: "+versionStr+" !");
    }
    VersionScheme.VersionSchemeBuilder builder = VersionScheme.builder();
    builder = builder.major(Integer.parseInt(versionGroup[0])).minor(Integer.parseInt(versionGroup[1]));
    if(versionGroup.length > 2){
      builder = builder.patch(Integer.parseInt(versionGroup[2]));
    }
    if(explode.length > 1){ //Meme detected!!!
      // Set desc
      StringBuilder descBuilder = new StringBuilder();
      for (int i = 0; i < explode.length; i++) {
        if(i == 0){
          continue;
        }
        descBuilder.append(explode[i]).append(" ");
      }
      builder = builder.desc(descBuilder.toString().trim());
    }
    return builder.build();
  }
}
