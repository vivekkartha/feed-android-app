package com.feed.app.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.feed.app.data.database.entity.Feed
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface FeedDao {
  @Insert
  fun insertAll(feed: Feed)

  @Query("SELECT * FROM Feed LIMIT 1")
  fun getAll(): Flowable<Feed>

  @Query("DELETE FROM Feed")
  fun deleteAll()
}