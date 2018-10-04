package com.feed.app.data.database;

import android.arch.persistence.room.TypeConverter;
import com.feed.app.data.database.entity.Feed;
import com.feed.app.data.database.entity.FeedItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FeedTypeConverter {

  private static Gson gson = new Gson();

  @TypeConverter
  public static List<FeedItem> toFeedItem(String data) {
    if (data == null) return Collections.emptyList();
    Type listType = new TypeToken<List<FeedItem>>() {
    }.getType();
    return gson.fromJson(data, listType);
  }

  @TypeConverter
  public static String toString(List<FeedItem> feedItem) {
    return gson.toJson(feedItem);
  }
}