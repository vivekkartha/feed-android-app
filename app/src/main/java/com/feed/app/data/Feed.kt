package com.feed.app.data

class Feed(
  var title: String,
  var rows: List<FeedItem>
)

class FeedItem(
  var title: String,
  var description: String,
  var imageHref: String
)