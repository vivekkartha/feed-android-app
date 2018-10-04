package com.feed.app.data

import com.feed.app.data.database.dao.FeedDao
import com.feed.app.data.database.entity.Feed
import com.feed.app.data.network.WebService
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class FeedRepository(
  private var webService: WebService,
  private var feedDao: FeedDao
) {
  fun getFeed(): Flowable<Feed> {
    return Flowable.mergeDelayError(
        webService.getAllFeed()
            .doOnNext(feedDao::insertAll)
            .subscribeOn(Schedulers.io()),
        feedDao.getAll()
            .subscribeOn(Schedulers.io())
    )
  }
}