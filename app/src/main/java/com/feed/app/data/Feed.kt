package com.feed.app.data

import android.arch.persistence.room.Entity

@Entity
class Feed(
  var title: String,
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