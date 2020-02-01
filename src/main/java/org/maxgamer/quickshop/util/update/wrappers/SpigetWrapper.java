package org.maxgamer.quickshop.util.update.wrappers;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.nonquickshopstuffs.com.sk89q.worldedit.util.net.HttpRequest;
import org.maxgamer.quickshop.util.update.Updater;
import org.maxgamer.quickshop.util.update.UpdaterWrapper;
import org.maxgamer.quickshop.util.update.VersionScheme;

public class SpigetWrapper implements UpdaterWrapper {
  private static final String UPDATE_URL =
      "https://api.spiget.org/v2/resources/62575/versions?size=1&sort=-releaseDate";
  private Gson gson = new Gson();

  private int downloads;
  private double rating;
  private long date;
  private VersionScheme version;

  @Override
  public boolean executeCheck() {
    try {
      String response =
          HttpRequest.get(new URL(UPDATE_URL))
              .execute()
              .expectResponseCode(200)
              .returnContent()
              .asString("UTF-8")
              .trim();
      UpdateResult result = gson.fromJson(response, UpdateResult.class);
      this.version = Updater.readVersionString(result.getName());
      this.downloads = result.getDownloads();
      this.date = result.getReleaseDate();
      this.rating = result.getRating().getAverage();
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
  public int getDownloads() {
    return this.downloads;
  }

  @Override
  public double getRating() {
    return this.rating;
  }

  @Override
  public long getReleaseDate() {
    return this.date;
  }
}

class UpdateResult {

  /**
   * downloads : 21 rating : {"count":0,"average":0} name : 3.1.3.3 releaseDate : 1580175348
   * resource : 62575 id : 311770
   */
  private int downloads;

  private RatingBean rating;
  private String name;
  private long releaseDate;
  private int resource;
  private int id;

  public int getDownloads() {
    return downloads;
  }

  public RatingBean getRating() {
    return rating;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getReleaseDate() {
    return releaseDate;
  }

  public int getResource() {
    return resource;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public static class RatingBean {

    /** count : 0 average : 0 */
    private int count;

    private double average;

    public int getCount() {
      return count;
    }

    public double getAverage() {
      return average;
    }
  }
}
