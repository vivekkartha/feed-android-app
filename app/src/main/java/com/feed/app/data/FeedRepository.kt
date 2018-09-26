package com.feed.app.data

import com.feed.app.data.network.WebService
import io.reactivex.Observable

class FeedRepository(private var webService: WebService) {
  fun getFeed(): Observable<Feed> = webService.getAllFeed()
}