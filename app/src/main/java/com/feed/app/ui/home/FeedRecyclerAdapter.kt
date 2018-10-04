package com.feed.app.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.feed.app.R
import com.feed.app.data.database.entity.FeedItem
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(
  private var feedList: List<FeedItem>,
  private var ctx: Context
) : RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_feed, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = feedList.size

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    val feed = feedList[position]
    holder.apply {
      Picasso.get()
          .load(feed.imageHref)
          .placeholder(R.drawable.ic_def)
          .error(R.drawable.ic_def)
          .into(ivFeed)
      tvTitle.text = feed.title
      tvDescription.text = feed.description
    }
  }

  inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val ivFeed: ImageView = v.findViewById(R.id.ivFeed)
    val tvTitle: TextView = v.findViewById(R.id.tvTitle)
    val tvDescription: TextView = v.findViewById(R.id.tvDescription)
  }
}