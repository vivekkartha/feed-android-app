package com.feed.app.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.feed.app.data.Feed
import com.feed.app.data.FeedRepository
import com.feed.app.utils.di.app
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private var feedRepository: FeedRepository = app().feedRepository) : ViewModel() {

  private var feedLiveData: MutableLiveData<Feed> = MutableLiveData()

  @SuppressLint("CheckResult")
  fun getFeed() {
    feedRepository.getFeed()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(feedLiveData::setValue)
  }
}