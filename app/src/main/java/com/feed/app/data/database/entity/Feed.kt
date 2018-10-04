package com.feed.app.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.feed.app.data.database.FeedTypeConverter

@Entity
class Feed(
  @PrimaryKey(autoGenerate = true)
  var id: Long,
  var title: String,
  @TypeConverters(FeedTypeConverter::class)
  var rows: List<FeedItem>
)
@Entity
class FeedItem(
  var title: String,
  var description: String,
  var imageHref: String
)

sealed class Status {
  data class ERROR(var msg: String) : Status()
  data class SUCCESS(var feed: Feed) : Status()
  object LOADING : Status()
}