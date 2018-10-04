package com.feed.app.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.feed.app.data.database.dao.FeedDao
import com.feed.app.data.database.entity.Feed

@Database(entities = [(Feed::class)], version = 1)
@TypeConverters(FeedTypeConverter::class)
abstract class FeedDatabase : RoomDatabase() {
  abstract fun feedDao(): FeedDao

  companion object {
    private var dbInstance: FeedDatabase? = null
    fun getInstance(context: Context): FeedDatabase {
      if (dbInstance == null) {
        dbInstance = Room.databaseBuilder(
            context.applicationContext,
            FeedDatabase::class.java, "feed.db"
        )
            .fallbackToDestructiveMigration()
            .build()
      }
      return dbInstance as FeedDatabase
    }
  }

}